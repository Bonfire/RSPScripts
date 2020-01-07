package com.bonfire;

import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.event.listeners.ChatMessageListener;
import org.rspeer.runetek.event.listeners.ItemTableListener;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.ChatMessageEvent;
import org.rspeer.runetek.event.types.ItemTableEvent;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.awt.*;
import java.time.LocalTime;

@ScriptMeta(developer = "Bonfire", name = "bonBeefCooker", desc = "Collects raw beef from the Lumbridge cow pens and cooks it for cooking XP.", version = 0.1)
public class bonBeefCooker extends TaskScript implements RenderListener, ChatMessageListener, ItemTableListener {

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

    private static int beefPickedUp, beefCooked, beefBurned;

    private Long startTime = 0L;
    private int rawBeefItemID = 2132;

    @Override
    public void onStart() {
        Log.fine("Starting bonBeefCooker");
        startTime = System.currentTimeMillis();
        submit(TASKS);
    }

    @Override
    public void onStop() {
        Log.fine("Stopping bonBeefCooker");
        super.onStop();
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics textGraphics = renderEvent.getSource();
        textGraphics.drawString("Beef Picked Up: " + beefPickedUp, 10, 40);
        textGraphics.drawString("Beef Cooked: " + beefCooked, 10, 60);
        textGraphics.drawString("Beef Burned: " + beefBurned, 10, 80);
        textGraphics.drawString("Time Elapsed: " + LocalTime.ofSecondOfDay((System.currentTimeMillis() - startTime) / 1000).toString(), 10, 100);
    }

    @Override
    public void notify(ChatMessageEvent chatMessageEvent) {
        // If we cook or burn beef, add to our count
        if (chatMessageEvent.getMessage().equalsIgnoreCase("You cook a piece of meat.")) {
            beefCooked++;
        }
        if (chatMessageEvent.getMessage().equalsIgnoreCase("You accidentally burn the meat.")) {
            beefBurned++;
        }
    }

    @Override
    public void notify(ItemTableEvent itemTableEvent) {
        // If we pick up beef, add one to our picked up count
        if (itemTableEvent.getChangeType() == ItemTableEvent.ChangeType.ITEM_ADDED
                && itemTableEvent.getDefinition().getName().equalsIgnoreCase("Raw beef")) {
            beefPickedUp++;
        }
    }
}
