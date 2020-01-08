package com.bonfire;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class WalkFromPen extends Task {

    @Override
    public boolean validate() {
        Player localPlayer = Players.getLocal();

        // If we're not at the cooking (range) area
        // and our inventory is full
        // and it's full of raw beef
        return !bonBeefCooker.getRangeArea().contains(localPlayer.getPosition())
                && Inventory.isFull()
                && (Inventory.getCount(false, "Raw beef") == 28);
    }

    @Override
    public int execute() {
        // Turn on run before we start if we can
        if (Movement.getRunEnergy() > Random.nextInt(7, 13) && !Movement.isRunEnabled()) {
            Log.fine("Turning on run");
            Movement.toggleRun(true);
            return Random.nextInt(1000, 2500);
        }

        // If we're executing, we need to walk to the cooking (range) area!
        Log.fine("Running to the Lumbridge range");
        Movement.walkToRandomized(bonBeefCooker.getRangeArea().getCenter());

        // Sleep for a random amount of time
        return Random.nextInt(1000, 3000);
    }
}
