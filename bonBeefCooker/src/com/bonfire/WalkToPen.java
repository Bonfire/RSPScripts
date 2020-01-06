package com.bonfire;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class WalkToPen extends Task {


    @Override
    public boolean validate() {
        Player localPlayer = Players.getLocal();

        // If our inventory is empty and we're not at the cow pen, then we should head there
        return Inventory.isEmpty() && !bonBeefCooker.getCowPenArea().contains(localPlayer.getPosition());
    }

    @Override
    public int execute() {
        // Turn on run before we start if we can
        if (Movement.getRunEnergy() > Random.nextInt(7, 13) && !Movement.isRunEnabled()) {
            Log.fine("Turning on run");
            Movement.toggleRun(true);
            return Random.nextInt(1000, 2500);
        }

        // If we're executing, we need to walk to the cow pen!
        Log.fine("Running to the cow pen");
        Movement.walkToRandomized(bonBeefCooker.getCowPenArea().getCenter());

        // Sleep for a random amount of time
        return Random.nextInt(1000, 3000);
    }
}
