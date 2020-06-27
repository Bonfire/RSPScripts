package com.bonfire;

import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Bonfire", name = "bonRaidScouter", desc = "Scouts the Chambers of Xeric for preferable raids", category = ScriptCategory.OTHER)
public class bonRaidScouter extends TaskScript {
    private static final Task[] TASKS = {new WalkToChambers()};

    @Override
    public void onStart() {
        Log.fine("Starting bonRaidScouter");
        submit(TASKS);
    }
}
