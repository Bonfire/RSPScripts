package com.bonfire;

import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Bonfire", name = "bonCooksAssistant", desc = "Completes the Cook's Assistant F2P quest", category = ScriptCategory.QUESTING, version = 0.11)
public class bonCooksAssistant extends TaskScript {

    private static final Task[] TASKS = {new WalkToKitchen(), new StartQuest(), new CollectStarterItems(), new WalkToCowPen(), new MilkCow(), new WalkToChickens(), new CollectEgg(), new WalkToWheatField(), new PickWheat()};

    static Area lumbridgeKitchen = Area.rectangular(3205, 3212, 3212, 3217);
    static Area lumbridgeCellar = Area.rectangular(3209, 9620, 3211, 9622);
    static Area lumbridgeCowPen = Area.rectangular(3256, 3270, 3253, 3273);
    static Area lumbridgeChickens = Area.rectangular(3227, 3301, 3233, 3295);
    static Area lumbridgeWheatField = Area.polygonal(
            new Position(3163, 3291, 0),
            new Position(3163, 3298, 0),
            new Position(3157, 3297, 0));

    static int cooksAssistantVarp = 29;

    @Override
    public void onStart() {
        Log.fine("Starting bonCooksAssistant");
        submit(TASKS);
    }
}
