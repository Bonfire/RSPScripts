package com.bonfire;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class WalkToChickens extends Task {
    @Override
    public boolean validate() {
        // If we have a bucket of milk and it's time to go grab an egg...
        Player localPlayer = Players.getLocal();

        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 1
                && Inventory.getCount(false, "Bucket of milk") >= 1
                && !bonCooksAssistant.lumbridgeChickens.contains(localPlayer.getPosition());
    }

    @Override
    public int execute() {
        Log.info("Heading to the Lumbridge chickens");
        Movement.walkToRandomized(bonCooksAssistant.lumbridgeChickens.getCenter());

        return Random.nextInt(1500,3000);
    }
}
