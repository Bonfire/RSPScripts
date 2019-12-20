package com.bonfire;

import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
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
                        && world.getActivity().contains("Wilderness") == scouterGUI.getWildernessCheckBox()
                        && world.isPVP() == scouterGUI.getpvpCheckBox());

        // Make sure to check if we need to confirm world hopping to PVP worlds
        // Note: This may cause the script to hang for one world hop as the interface does not show until
        //          choose to hop to the world (+1 count to hop, +1 count to confirm and actually hop)
        if (Interfaces.getComponent(193, 0, 3) != null && Interfaces.getComponent(193,0,3).isVisible()){
            Interfaces.getComponent(193, 0, 3).interact("Switch to it");
        }

        // Increment the world count
        bonScouter.incrementWorld();

        // Notify the Scan task that it is able to scan
        bonScouter.setScanComplete(false);

        return Random.nextInt(1000,3000);
    }
}
