package com.bonfire;

import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class CollectEgg extends Task {
    @Override
    public boolean validate() {
        // If we have a bucket of milk and we're at the chickens, and we don't have an egg, we should grab one
        Player localPlayer = Players.getLocal();

        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 1
                && Inventory.getCount(false, "Bucket of milk") >= 1
                && bonCooksAssistant.lumbridgeChickens.contains(localPlayer.getPosition())
                && Inventory.getCount(false, "Egg") < 1;
    }

    @Override
    public int execute() {
        Log.info("Locating a nearby egg");

        Pickable nearbyEgg = Pickables.getNearest("Egg");
        if (nearbyEgg != null && bonCooksAssistant.lumbridgeChickens.contains(nearbyEgg.getPosition())) {
            Log.info("Going to pick up egg");
            nearbyEgg.click();
        }

        return Random.nextInt(1500,3000);
    }
}
