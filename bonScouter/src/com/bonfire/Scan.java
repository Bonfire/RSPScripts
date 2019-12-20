package com.bonfire;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Definitions;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.InterfaceAddress;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static org.rspeer.runetek.api.commons.Time.sleepUntil;

public class Scan extends Task {
    private static ArrayList<String> valuableItems = new ArrayList<String>();
    private static Boolean beginScanning = null;

    public static void updateValuableItems() {
        // Populate the valuable items list, reading from the GUI component
        String valuableItemsList = scouterGUI.getItemTextField();
        String[] splitValuables = valuableItemsList.split("\\s*,\\s*");
        valuableItems.addAll(Arrays.asList(splitValuables));
        beginScanning = true;
    }

    @Override
    public boolean validate() {
        // Only run when the Hop task tells us to scan
        return !bonScouter.isScanComplete()
                && Game.getState() != Game.STATE_HOPPING_WORLD
                && Game.getState() != Game.STATE_LOADING_REGION
                && beginScanning != null
                && beginScanning;
    }

    @Override
    public int execute() {
        // Wait until we're done hopping
        sleepUntil(() -> Game.getState() == Game.STATE_IN_GAME, Random.nextInt(1000, 3000));

        // Notify the user that we are beginning our scan
        Log.fine("Scanning World " + Worlds.getCurrent() + "...");

        // Get all nearby players (check to make sure they're valid by grabbing their name)
        // Also verify that we're not scanning ourselves, so filter ourselves out
        Player[] nearbyPlayers = Players.getLoaded(player -> !player.getName().isEmpty() && player != Players.getLocal());

        // For each player, increment our count and check their equipment
        for (Player nearbyPlayer : nearbyPlayers) {
            bonScouter.incrementPlayersScanned();

            // A list that holds the players equipment
            List<String> playerEquipment = getPlayerEquipment(nearbyPlayer);

            // If the player has an item that we're looking for...
            if (playerEquipment.stream().anyMatch(item -> valuableItems.contains(item))) {
                String playersItemsString = String.join(", ", playerEquipment);
                int currentWorld = Worlds.getCurrent();

                // Call out the player in the debug console
                Log.info(nearbyPlayer.getName() + " | World " + currentWorld + " | Combat Level " + nearbyPlayer.getCombatLevel() + " | " + (nearbyPlayer.getSkullIcon() == 0 ? "Skulled" : "Not Skulled") + " | " + playersItemsString);

                // Increment the targeted player count and send a Discord message if enabled
                bonScouter.incrementPlayersTargeted();

                if (bonScouter.isUseWebhook() != null && bonScouter.isUseWebhook()) {
                    DiscordWebhook discordWebhook = new DiscordWebhook(bonScouter.getWebhookURL());
                    discordWebhook.setUsername("bonScouter");

                    DiscordWebhook.EmbedObject scoutEmbed = new DiscordWebhook.EmbedObject();
                    scoutEmbed.setColor(Color.GREEN);
                    scoutEmbed.setTitle("Player Target");
                    scoutEmbed.setDescription("A new player has been targeted");
                    scoutEmbed.addField("Name", nearbyPlayer.getName(), true);
                    scoutEmbed.addField("World", Integer.toString(currentWorld), true);
                    scoutEmbed.addField("Skulled", (nearbyPlayer.getSkullIcon() == 0 ? "Yes" : "No"), true);
                    scoutEmbed.addField("Combat Level", Integer.toString(nearbyPlayer.getCombatLevel()), true);
                    scoutEmbed.addField("Target", (nearbyPlayer.getTarget() != null ? nearbyPlayer.getTarget().getName() : "None"), true);
                    scoutEmbed.addField("Scouted Item", (playerEquipment.stream().filter(item -> valuableItems.contains(item)).findFirst().get()), true);
                    scoutEmbed.addField("Items", playersItemsString, false);

                    try {
                        discordWebhook.addEmbed(scoutEmbed);
                        discordWebhook.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        // Notify the Hop task that we're done scanning and sleep
        bonScouter.setScanComplete(true);
        return Random.nextInt(4000,6000);
    }

    // Thank you to "slut" for this snippet
    // https://discourse.rspeer.org/t/grabbing-player-equipment/2334
    private List<String> getPlayerEquipment(Player p) {
        List<String> equipmentList = new LinkedList<>();
        if (p != null) {
            int[] equipment = p.getAppearance().getEquipmentIds();
            for (int i = 0; i < equipment.length; i++) {
                if (equipment[i] - 512 > 0) {
                    equipmentList.add(Definitions.getItem(equipment[i] - 512).getName());
                }
            }
        }
        return equipmentList;
    }
}
