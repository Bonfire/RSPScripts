package com.bonfire;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class WalkToWheatField extends Task {
    @Override
    public boolean validate() {
        // If we have a bucket of milk and an egg, but no flour, and we're not in the wheat field
        // Add a secondary position check for the top of the mill, to make sure we don't stop the MillGrain task
        // Add a tertiary position check for the bottom of the mill to make sure we don't stop the MillGrain task
        // Add a fourth position check for the kitchen to make sure we don't stop the EndQuest task
        Player localPlayer = Players.getLocal();

        // TODO: Refine this to catch edge cases
        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 1
                && Inventory.getCount(false, "Bucket of milk") >= 1
                && Inventory.getCount(false, "Egg") >= 1
                && Inventory.getCount(false, "Grain") < 1
                && !bonCooksAssistant.lumbridgeWheatField.contains(localPlayer.getPosition())
                && !bonCooksAssistant.lumbridgeMillTop.contains(localPlayer.getPosition())
                && !bonCooksAssistant.lumbridgeMillBottom.contains(localPlayer.getPosition())
                && !bonCooksAssistant.lumbridgeKitchen.contains(localPlayer.getPosition());
    }

    @Override
    public int execute() {
        Log.info("Heading to the Lumbridge wheat field");
        Movement.walkToRandomized(bonCooksAssistant.lumbridgeWheatField.getCenter());

        return Random.nextInt(1500,3000);
    }
}
