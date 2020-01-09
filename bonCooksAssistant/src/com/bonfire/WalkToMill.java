package com.bonfire;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class WalkToMill extends Task {
    @Override
    public boolean validate() {
        // If we have a bucket of milk, an egg, grain, and we're not in the flour mill
        Player localPlayer = Players.getLocal();

        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 1
                && Inventory.getCount(false, "Bucket of milk") >= 1
                && Inventory.getCount(false, "Egg") >= 1
                && Inventory.getCount(false, "Grain") >= 1
                && !bonCooksAssistant.lumbridgeMillTop.contains(localPlayer.getPosition());
    }

    @Override
    public int execute() {
        Log.info("Heading to the top floor of the Lumbridge flour mill");
        Movement.walkToRandomized(bonCooksAssistant.lumbridgeMillTop.getCenter());

        return Random.nextInt(1500,3000);
    }
}
