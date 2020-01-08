package com.bonfire;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class WalkToCowPen extends Task {
    @Override
    public boolean validate() {
        // Make sure the quest is started and we have the starter items, and that we're NOT at the cow pen
        Player localPlayer = Players.getLocal();

        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 1
                && Inventory.getCount(false, "Pot") >= 1
                && Inventory.getCount(false, "Bucket") >= 1
                && !bonCooksAssistant.lumbridgeCowPen.contains(localPlayer.getPosition());
    }

    @Override
    public int execute() {
        Log.info("Heading to the Lumbridge cow pen");
        Movement.walkToRandomized(bonCooksAssistant.lumbridgeCowPen.getCenter());

        return Random.nextInt(1500,3000);
    }
}
