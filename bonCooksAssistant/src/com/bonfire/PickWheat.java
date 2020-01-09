package com.bonfire;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class PickWheat extends Task {
    @Override
    public boolean validate() {
        // If we have a pot, a bucket of milk, an egg, no grain (picked wheat), and we're in the wheat field
        Player localPlayer = Players.getLocal();

        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 1
                && Inventory.getCount(false, "Pot") >= 1
                && Inventory.getCount(false, "Bucket of milk") >= 1
                && Inventory.getCount(false, "Egg") >= 1
                && Inventory.getCount(false, "Grain") < 1
                && bonCooksAssistant.lumbridgeWheatField.contains(localPlayer.getPosition());

    }

    @Override
    public int execute() {
        Log.info("Locating a nearby piece of wheat");

        // Find a nearby dairy cow and milk it!
        SceneObject nearbyWheat = SceneObjects.getNearest("Wheat");

        if (nearbyWheat != null) {
            Log.info("Picking one wheat");
            nearbyWheat.interact("Pick");
        }

        return Random.nextInt(1500,3000);
    }
}
