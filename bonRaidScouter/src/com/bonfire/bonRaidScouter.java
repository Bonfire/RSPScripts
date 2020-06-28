package com.bonfire;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

import static org.rspeer.runetek.api.commons.Time.sleepUntil;

@ScriptMeta(developer = "Bonfire", name = "bonRaidScouter", desc = "Scouts the Chambers of Xeric for preferable raids", category = ScriptCategory.OTHER)
public class bonRaidScouter extends Script {
    final Area raidsArea = Area.rectangular(1229, 3574, 1256, 3553);
    final int inRaidVarbit = 5432;
    final int partySizeVarbit = 5424;
    boolean isRaidSolved = false;

    @Override
    public void onStart() {
        Log.fine("Starting bonRaidScouter");
    }

    @Override
    public int loop() {
        boolean isInRaid = (Varps.getBitValue(inRaidVarbit) == 1);

        Player localPlayer = Players.getLocal();

        Log.info("In Raid: " + Varps.getBitValue(inRaidVarbit)  + " | Party Size: " + Varps.getBitValue(partySizeVarbit));

        // Move the player to the raids area only if they're not there
        if (!raidsArea.contains(localPlayer.getPosition()) && !isInRaid) {
            Log.info("Heading to Mount Quidamortem");
            Movement.walkToRandomized(raidsArea.getCenter());
            return Random.nextInt(3000,5000);
        }

        // Create a new party and enter the raid
        if (raidsArea.contains(localPlayer.getPosition()) && !isInRaid) {
            SceneObject recruitingBoard = SceneObjects.getNearest("Recruiting board");

            // If we found the recruiting board, open the board
            if (recruitingBoard != null) {
                recruitingBoard.interact("Read");
                sleepUntil(() -> Interfaces.isVisible(499,58), Random.nextInt(3000, 5000));
            }

            InterfaceComponent makePartyButton = Interfaces.getComponent(499,58);

            if (Interfaces.isVisible(makePartyButton.toAddress())) {
                makePartyButton.click();
                Interfaces.closeAll();
            }

            SceneObject chambersDoor = SceneObjects.getNearest("Chambers of Xeric");
            if (chambersDoor != null) {
                chambersDoor.interact("Enter");
                sleepUntil(() -> Varps.getBitValue(inRaidVarbit) == 1, 5000);
            }
        }

        // Solve the raid
        if (isInRaid && !isRaidSolved) {

        }

        // Exit the raid
        if (isInRaid && isRaidSolved){
            Log.info("Raid solved. Scouting new raid.");

            SceneObject exitSteps = SceneObjects.getNearest("Steps");
            if (exitSteps != null) {
                exitSteps.interact("Climb");
                sleepUntil(Dialog::isViewingChatOptions, 5000);
            }

            if(Dialog.isViewingChatOptions()) {
                Dialog.process(option -> option.contains("Leave"));
                sleepUntil(() -> Varps.getBitValue(inRaidVarbit) == 0, 5000);
            }
        }

        return Random.nextInt(3000,5000);
    }
}
