package com.bonfire;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class MillGrain extends Task {
    @Override
    public boolean validate() {
        // If we have a bucket of milk, an egg, and we're at the top floor of the flour mill
        Player localPlayer = Players.getLocal();

        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 1
                && Inventory.getCount(false, "Bucket of milk") >= 1
                && Inventory.getCount(false, "Egg") >= 1
                && bonCooksAssistant.lumbridgeMillTop.contains(localPlayer.getPosition());
    }

    @Override
    public int execute() {
        // TODO: Fix this to make it less likely that the timing may be off. Guarantee that the flow will be correct

        // If we haven't deposited our grain yet
        if (Inventory.getCount(false, "Grain") >= 1) {
            Log.info("Locating the hopper");
            SceneObject nearbyHopper = SceneObjects.getNearest("Hopper");

            if (nearbyHopper != null) {
                Log.info("Depositing grain into the hopper");
                nearbyHopper.interact("Fill");

                return Random.nextInt(3000,5000);
            }
        }

        // If we've already deposited the grain, we should instead pull the levers
        if (Inventory.getCount(false, "Grain") < 1) {
            Log.info("Locating the hopper controls");
            SceneObject nearbyHopper = SceneObjects.getNearest("Hopper controls");

            if (nearbyHopper != null) {
                Log.info("Operating the hopper controls");
                nearbyHopper.interact("Operate");

                // Wait until we're done operating, then head down to the bottom floor
                Time.sleepUntil(() -> !Players.getLocal().isAnimating(), 500,5000);

                // Head to the bottom floor
                Log.info("Heading to the bottom floor of the Lumbridge flour mill");
                Movement.walkToRandomized(bonCooksAssistant.lumbridgeMillBottom.getCenter());

                // Wait until we're at the bottom floor
                Time.sleepUntil(() -> bonCooksAssistant.lumbridgeMillBottom.contains(Players.getLocal().getPosition()), 500, 10000);

                // Collect the flour
                Log.info("Locating the flour bin");
                SceneObject flourBin = SceneObjects.getNearest("Flour bin");

                if (flourBin != null) {
                    Log.info("Collecting flour");
                    flourBin.interact("Empty");
                }

                return Random.nextInt(1500,3000);
            }
        }

        return Random.nextInt(1500,3000);
    }
}
