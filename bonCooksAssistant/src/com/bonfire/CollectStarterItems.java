package com.bonfire;

import org.rspeer.runetek.adapter.Interactable;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class CollectStarterItems extends Task {
    @Override
    public boolean validate() {
        // If the quest has been started (Varp value = 1) and we're in the kitchen or cellar and we don't have the starter items
        Player localPlayer = Players.getLocal();

        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 1
                && (bonCooksAssistant.lumbridgeKitchen.contains(localPlayer.getPosition()) || bonCooksAssistant.lumbridgeCellar.contains(localPlayer.getPosition()))
                && (Inventory.getCount(false, "Pot") < 1 || Inventory.getCount(false, "Bucket") < 1);
    }

    @Override
    public int execute() {
        // If we don't have a pot...
        if (Inventory.getCount(false, "Pot") < 1) {
            // Grab the nearby pot if it exists. Make sure to check if it's the one in the kitchen
            Log.info("Finding the nearby pot");
            Pickable nearbyPot = Pickables.getNearest("Pot");
            if (nearbyPot != null && bonCooksAssistant.lumbridgeKitchen.contains(nearbyPot.getPosition())) {
                Log.info("Picking up the pot");
                nearbyPot.click();
                return Random.nextInt(1500,3000);
            }
        }

        // If we don't have a bucket...
        if (Inventory.getCount(false, "Bucket") < 1) {
            Log.info("Heading to the cellar to grab a bucket");

            // Head down to the cellar to go pick up a bucket
            Movement.walkToRandomized(bonCooksAssistant.lumbridgeCellar.getCenter());

            // Wait until we get there
            Time.sleepUntil(() -> !Players.getLocal().isMoving(), 500, 5000);

            Pickable nearbyBucket = Pickables.getNearest("Bucket");
            if (nearbyBucket != null) {
                Log.info("Picking up the bucket");
                nearbyBucket.click();
                return Random.nextInt(1500,3000);
            }
        }

        return Random.nextInt(2500,5000);
    }
}
