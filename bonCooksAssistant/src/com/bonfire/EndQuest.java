package com.bonfire;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class EndQuest extends Task {
    @Override
    public boolean validate() {
        // If we have all required items and we're in the kitchen, let's finish the quest
        Player localPlayer = Players.getLocal();

        return Varps.get(bonCooksAssistant.cooksAssistantVarp) == 1
                && (Inventory.getCount(false, "Bucket of milk") >= 1
                || Inventory.getCount(false, "Egg") >= 1
                || Inventory.getCount(false, "Pot of flour") >= 1)
                || Dialog.isOpen()
                && bonCooksAssistant.lumbridgeKitchen.contains(localPlayer.getPosition());
    }

    @Override
    public int execute() {
        // If we haven't started talking to the cook yet, talk to him
        if (!Dialog.isOpen()){
            Log.info("Locating the cook");
            Npc lumbridgeCook = Npcs.getNearest("Cook");

            if (lumbridgeCook != null) {
                Log.info("Talking to the cook");
                lumbridgeCook.interact("Talk-to");
                return Random.nextInt(1500, 3000);
            }
        } else {
            // If we can continue in the dialog, do so
            if (Dialog.canContinue()) {
                Log.info("Processing a continue");
                Dialog.processContinue();
                return Random.nextInt(500,1500);
            }

            // If we need to select a chat option, pick the correct one
            // In this case, the correct chat option is the first one (index 0) for both questions
            if (Dialog.isViewingChatOptions()) {
                Log.info("Selecting the correct chat option");
                Dialog.process(0);
                return Random.nextInt(1500, 3000);
            }
        }

        // Should only get down here once the quest has begun
        // Return a random wait time after the task is done executing
        return Random.nextInt(500,2500);
    }
}
