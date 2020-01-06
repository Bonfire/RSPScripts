package com.bonfire;

import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Collect extends Task {
    private Area cowPenArea = Area.polygonal(
            new Position(3265, 3296, 0),
            new Position(3265, 3255, 0),
            new Position(3253, 3255, 0),
            new Position(3253, 3272, 0),
            new Position(3251, 3274, 0),
            new Position(3251, 3276, 0),
            new Position(3249, 3278, 0),
            new Position(3246, 3278, 0),
            new Position(3244, 3280, 0),
            new Position(3244, 3281, 0),
            new Position(3240, 3285, 0),
            new Position(3240, 3286, 0),
            new Position(3241, 3287, 0),
            new Position(3241, 3288, 0),
            new Position(3242, 3289, 0),
            new Position(3242, 3292, 0),
            new Position(3242, 3293, 0),
            new Position(3241, 3294, 0),
            new Position(3241, 3295, 0),
            new Position(3240, 3296, 0),
            new Position(3240, 3297, 0),
            new Position(3240, 3298, 0),
            new Position(3241, 3298, 0),
            new Position(3263, 3298, 0));

    @Override
    public boolean validate() {
        Player localPlayer = Players.getLocal();

        // If we're in the cow pen
        // and we don't have a full inventory of raw beef
        // and our inventory isn't full
        return (cowPenArea.contains(localPlayer.getPosition())
                && (Inventory.getCount(false, "Raw beef") < 28)
                && !Inventory.isFull());
    }

    @Override
    public int execute() {
        // Find the nearest raw beef!
        Pickable closestBeef = Pickables.getNearest("Raw beef");

        // Pick up the nearest raw beef if there exists one and it's inside the pen
        if (closestBeef != null && cowPenArea.contains(closestBeef.getPosition())){
            Log.fine("Found beef, going to pick up");
            closestBeef.click();
        } else {
            Log.fine("Couldn't find any beef, walking around");
            // If we couldn't find anything nearby, try walking around the cow pen
            Movement.walkToRandomized(cowPenArea.getCenter());
            Random.nextInt(1000,2500);
        }

        // Wait until the player is done moving until we go to pick up another one
        Time.sleepUntil(() -> Players.getLocal().isMoving(), 500, 5000);

        // Sleep before executing again
        return Random.nextInt(1000,2500);
    }
}
