package com.bonfire;

import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.ItemTableListener;
import org.rspeer.runetek.event.types.ItemTableEvent;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Collect extends Task {

    Position[] randomCollectionTiles = {
            new Position(3243, 3296, 0),
            new Position(3261, 3291, 0),
            new Position(3245, 3286, 0),
            new Position(3260, 3281, 0),
            new Position(3252, 3276, 0),
            new Position(3260, 3271, 0),
            new Position(3255, 3266, 0),
            new Position(3261, 3261, 0),
            new Position(3256, 3257, 0)
    };

    @Override
    public boolean validate() {
        Player localPlayer = Players.getLocal();

        // If we're in the cow pen
        // and we don't have a full inventory of raw beef
        // and our inventory isn't full
        return (bonBeefCooker.getCowPenArea().contains(localPlayer.getPosition())
                && (Inventory.getCount(false, "Raw beef") < 28)
                && !Inventory.isFull());
    }

    @Override
    public int execute() {
        // Turn on run if we can
        if (Movement.getRunEnergy() > Random.nextInt(7, 13) && !Movement.isRunEnabled()) {
            Log.fine("Turning on run");
            Movement.toggleRun(true);
            return Random.nextInt(1000, 2500);
        }

        Player localPlayer = Players.getLocal();

        // If the person is running, just return, we're already doing something
        if (localPlayer.isMoving()){
            return Random.nextInt(1000,2500);
        }

        // Find the nearest raw beef!
        Pickable closestBeef = Pickables.getNearest("Raw beef");

        // Pick up the nearest raw beef if there exists one and it's inside the pen
        if (closestBeef != null && bonBeefCooker.getCowPenArea().contains(closestBeef.getPosition())) {
            Log.fine("Walking to beef and picking up");
            closestBeef.click();

            // Wait until the player is done moving until we go to pick up another one
            Time.sleepUntil(() -> !Players.getLocal().isMoving(), 500, 10000);
        } else {
            Log.fine("Couldn't find any beef... Walking to a random tile");
            Movement.walkToRandomized(Random.nextElement(randomCollectionTiles));

            // If we couldn't find anything nearby, just wait
            Random.nextInt(2500, 5000);
        }

        // Sleep before executing again
        return Random.nextInt(1000, 2500);
    }
}
