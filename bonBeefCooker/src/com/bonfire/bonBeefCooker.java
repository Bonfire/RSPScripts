package com.bonfire;

import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Bonfire", name = "bonBeefCooker", desc = "Collects raw beef from the Lumbridge cow pens and cooks it for cooking XP.", version = 0.1)
public class bonBeefCooker extends TaskScript {

    private static final Task[] TASKS = {new WalkToPen(), new Collect(), new WalkFromPen(), new Cook()};

    @Override
    public void onStart() {
        Log.fine("Starting bonBeefCooker");
        submit(TASKS);
    }
}
