package com.bonfire;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.chatter.BefriendedPlayers;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.runetek.providers.RSBefriendedPlayer;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

public class Cook extends Task {
    private final Predicate<Item> itemsToDrop = item -> item.getName().equals("Cooked meat") || item.getName().equals("Burnt meat");


    @Override
    public boolean validate() {
        Player localPlayer = Players.getLocal();

        // If we're at the range and we still have beef to cook we should execute
        return bonBeefCooker.getRangeArea().contains(localPlayer.getPosition())
                && (Inventory.contains("Raw beef")
                || (Inventory.contains("Burnt meat"))
                || (Inventory.contains("Cooked meat")));
    }

    @Override
    public int execute() {
        bonBeefCooker.setCurrentTask("Cooking");

        // If we're already cooking, just return
        Player localPlayer = Players.getLocal();
        if (localPlayer.isAnimating()) {
            return Random.nextInt(2500, 5000);
        }
        
        // If we've cooked all of our meat, drop it all
        if (Inventory.getCount(false, "Raw beef") == 0) {
            powerDrop(itemsToDrop, Random.high(75, 180), Random.high(180, 220));
            return 1000;
        }
        
        SceneObject cookingRange = SceneObjects.getNearest("Range");
        if (cookingRange != null) {
            Log.info("Clicking on the range");
            // Click on the range and begin cooking
            cookingRange.interact("Cook");
            Time.sleepUntil(Production::isOpen, 500, 5000);
            if (Production.isOpen()) {
                Log.info("Cooking beef");
                Production.initiate(itemToCook -> itemToCook.getName().equalsIgnoreCase("Raw beef") || itemToCook.getName().equalsIgnoreCase("Cooked meat"));
            }
        }

        return Random.nextInt(2500, 5000);
    }

    // Thank you to user "jrtn" for this power-dropping snippet
    private void powerDrop(Predicate<Item> condition, int minSleep, int maxSleep) {
        int[] order = {
                0, 1, 4, 5, 8, 9, 12, 13, 16, 17, 20, 21, 24, 25,
                2, 3, 6, 7, 10, 11, 14, 15, 18, 19, 22, 23, 26, 27
        };
        for (int i : order) {
            Item item = Inventory.getItemAt(i);
            if (item != null && condition.test(item)) {
                item.interact("Drop");
                Time.sleep(minSleep, maxSleep);
            }
        }
    }

}
