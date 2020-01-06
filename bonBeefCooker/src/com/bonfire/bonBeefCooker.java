package com.bonfire;

import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Bonfire", name = "bonBeefCooker", desc = "Collects raw beef from the Lumbridge cow pens and cooks it for cooking XP.", version = 0.1)
public class bonBeefCooker extends TaskScript {

    private static final Task[] TASKS = {new WalkToPen(), new Collect(), new WalkFromPen(), new Cook()};

    private static final Area rangeArea = Area.rectangular(3230, 3198, 3237, 3195);

    private static final Area cowPenArea = Area.polygonal(
            new Position(3265, 3296, 0),
            new Position(3265, 3255, 0),
            new Position(3253, 3255, 0),
            new Position(3253, 3272, 0),
            new Position(3251, 3274, 0),
            new Position(3251, 3276, 0),
            new Position(3249, 3278, 0),
            new Position(3246, 3278, 0),
            new Position(3244, 3280, 0),
            new Position(3244, 3281, 0),
            new Position(3240, 3285, 0),
            new Position(3240, 3286, 0),
            new Position(3241, 3287, 0),
            new Position(3241, 3288, 0),
            new Position(3242, 3289, 0),
            new Position(3242, 3292, 0),
            new Position(3242, 3293, 0),
            new Position(3241, 3294, 0),
            new Position(3241, 3295, 0),
            new Position(3240, 3296, 0),
            new Position(3240, 3297, 0),
            new Position(3240, 3298, 0),
            new Position(3241, 3298, 0),
            new Position(3263, 3298, 0));

    static Area getRangeArea() {
        return rangeArea;
    }

    static Area getCowPenArea() {
        return cowPenArea;
    }

    @Override
    public void onStart() {
        Log.fine("Starting bonBeefCooker");
        submit(TASKS);
    }

}
