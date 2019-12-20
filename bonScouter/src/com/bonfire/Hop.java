package com.bonfire;

import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Hop extends Task {
    @Override
    public boolean validate() {
        return bonScouter.isScanComplete() && bonScouter.isUseWebhook() != null && Game.isLoggedIn();
    }

    @Override
    public int execute() {
        // Hop worlds and increment the world hop count
        Log.fine("Hopping worlds...");

        // Get world filters from user's input and hop to the worlds filtered by the predicate
        WorldHopper.randomHop(world -> world.isMembers() == scouterGUI.getMembersCheckBox()
                        && world.getActivity().contains("Wilderness") == scouterGUI.getWildernessCheckBox());

        // Increment the world count
        bonScouter.incrementWorld();

        // Notify the Scan task that it is able to scan
        bonScouter.setScanComplete(false);

        return Random.nextInt(1000,3000);
    }
}
