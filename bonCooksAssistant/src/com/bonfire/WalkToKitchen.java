package com.bonfire;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class WalkToKitchen extends Task {

    @Override
    public boolean validate() {
        // If the quest hasn't been started yet and we're not in the kitchen we should run this task
        // 0 means the quest hasn't been started, 1 means the quest has been started
        Player localPlayer = Players.getLocal();

        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 0
                && !bonCooksAssistant.lumbridgeKitchen.contains(localPlayer.getPosition());
    }

    @Override
    public int execute() {
        // We should run to the kitchen to talk to the cook!
        Log.info("Running to the Lumbridge castle kitchen");
        Movement.walkToRandomized(bonCooksAssistant.lumbridgeKitchen.getCenter());
        Time.sleepUntil(() -> !Players.getLocal().isMoving(), 500, 5000);
        return Random.nextInt(1500,2500);
    }
}
