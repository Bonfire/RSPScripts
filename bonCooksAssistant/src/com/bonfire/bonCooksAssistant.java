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

    private static final Task[] TASKS = {new WalkToKitchen(), new StartQuest(), new CollectStarterItems(), new WalkToCowPen(), new MilkCow(), new WalkToChickens(), new CollectEgg(), new WalkToWheatField(), new PickWheat(), new WalkToMill(), new MillGrain(), new EndQuest()};

    static final Area lumbridgeKitchen = Area.rectangular(3205, 3212, 3212, 3217);
    static final Area lumbridgeCellar = Area.rectangular(3209, 9620, 3211, 9622);
    static final Area lumbridgeCowPen = Area.rectangular(3256, 3270, 3253, 3273);
    static final Area lumbridgeChickens = Area.rectangular(3227, 3301, 3233, 3295);
    static final Area lumbridgeWheatField = Area.polygonal(
            new Position(3163, 3291, 0),
            new Position(3163, 3298, 0),
            new Position(3157, 3297, 0));
    static final Area lumbridgeMillTop = Area.rectangular(3165, 3308, 3168, 3305, 2);
    static final Area lumbridgeMillBottom = Area.rectangular(3164, 3308, 3168, 3305);

    static final int cooksAssistantVarp = 29;

    @Override
    public void onStart() {
        Log.fine("Starting bonCooksAssistant");
        submit(TASKS);
    }
}
