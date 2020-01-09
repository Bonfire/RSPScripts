package com.bonfire;

import org.rspeer.runetek.adapter.Interactable;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class MilkCow extends Task {
    @Override
    public boolean validate() {
        // Make sure the quest is started and we have the starter items,
        // and that we ARE at the cow pen, and that we don't have a bucket of milk
        Player localPlayer = Players.getLocal();

        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 1
                && Inventory.getCount(false, "Pot") >= 1
                && Inventory.getCount(false, "Bucket") >= 1
                && bonCooksAssistant.lumbridgeCowPen.contains(localPlayer.getPosition())
                && Inventory.getCount(false, "Bucket of milk") < 1;
    }

    @Override
    public int execute() {
        Log.info("Locating a nearby dairy cow");

        // Find a nearby dairy cow and milk it!
        SceneObject dairyCow = SceneObjects.getNearest("Dairy cow");

        if (dairyCow != null) {
            Log.info("Milking the cow");
            dairyCow.interact("Milk");
        }

        return Random.nextInt(1500,3000);
    }
}
