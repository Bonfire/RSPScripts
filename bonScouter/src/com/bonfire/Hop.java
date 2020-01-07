package com.bonfire;

import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.InterfaceAddress;
import org.rspeer.runetek.api.component.InterfaceComposite;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Hop extends Task {
    public static final InterfaceAddress SWITCH_TO_IT_BUTTON_ADDRESS = new InterfaceAddress(() -> {
        return Interfaces.getFirst(InterfaceComposite.ITEM_DIALOG.getGroup(), 0, a -> a.containsAction("Switch to it"));
    });

    @Override
    public boolean validate() {
        // Here we do a null-check for isUseWebhook to make sure the user has saved their settings in the GUI
        // isUseWebhook can be true or false, but is only null when the user did not click the save button in the GUI
        return bonScouter.isScanComplete() && bonScouter.isUseWebhook() != null && Game.isLoggedIn();
    }

    @Override
    public int execute() {
        // Hop worlds and increment the world hop count
        Log.fine("Hopping worlds...");

        boolean P2PCheck = scouterGUI.getMembersCheckBox();
        boolean PVPCheck = scouterGUI.getPVPCheckBox();
        boolean BountyCheck = scouterGUI.getBountyCheckBox();

        // Keep track of the current world to see if we changed worlds
        int currentWorld = Worlds.getCurrent();

        // Get world filters from user's input and hop to the worlds filtered by the predicate
        // TODO: Make the predicates for randomHop not loose (remove ORs) and replace with more sound logic
        //  that has strict filter for worlds. May need help with strict filtering!
        // An example of unwanted behavior referenced in the above comment is that if you select PVP Worlds
        //  and Bounty Worlds, it'll try to join PVP Worlds that are members because technically P2P PVP worlds
        //  are still PVP worlds. This applies to all three filters below due to the ORs. ANDs do not fix this.
        WorldHopper.randomHop(rsWorld
                -> (rsWorld.getId() != currentWorld)
                && ((rsWorld.isMembers() == P2PCheck)
                || (rsWorld.isPVP() == PVPCheck)
                || (rsWorld.isBounty() == BountyCheck)));

        // Make sure to check if we need to confirm world hopping to PVP worlds
        // Note: This may cause the script to hang for one world hop as the interface does not show until
        //  choose to hop to the world (+1 count to hop, +1 count to confirm and actually hop)
        if (SWITCH_TO_IT_BUTTON_ADDRESS.resolve() != null) {
            SWITCH_TO_IT_BUTTON_ADDRESS.resolve().click();
        }

        // Increment the world count
        bonScouter.incrementWorld();

        // Notify the Scan task that it is able to scan
        bonScouter.setScanComplete(false);

        return Random.nextInt(1000, 3000);
    }
}
