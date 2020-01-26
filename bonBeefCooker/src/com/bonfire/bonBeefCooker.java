package com.bonfire;

import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.event.listeners.ChatMessageListener;
import org.rspeer.runetek.event.listeners.ItemTableListener;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.ChatMessageEvent;
import org.rspeer.runetek.event.types.ItemTableEvent;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.awt.*;
import java.time.LocalTime;

import static org.rspeer.runetek.api.component.tab.Skills.getExperience;

@ScriptMeta(developer = "Bonfire", name = "bonBeefCooker", desc = "Collects raw beef from the Lumbridge cow pens and cooks it at the range for cooking XP.", category = ScriptCategory.COOKING, version = 1.1)
public class bonBeefCooker extends TaskScript implements RenderListener, ChatMessageListener, ItemTableListener {

    private static final Task[] TASKS = {new WalkToPen(), new Collect(), new WalkFromPen(), new Cook()};

    private static final Area rangeArea = Area.rectangular(3231, 3197, 3236, 3196);

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
    private int startCookingXP;
    private int startCookingLevel;
    private static String currentTask = "None";
    private static StopWatch stopWatch;

    static void setCurrentTask(String currentTask) {
        bonBeefCooker.currentTask = currentTask;
    }

    @Override
    public void onStart() {
        Log.fine("Starting bonBeefCooker");
        stopWatch = StopWatch.start();
        startTime = System.currentTimeMillis();
        startCookingXP = getExperience(Skill.COOKING);
        startCookingLevel = Skills.getVirtualLevel(Skill.COOKING);
        submit(TASKS);
    }

    @Override
    public void onStop() {
        Log.fine("Stopping bonBeefCooker");
        super.onStop();
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics graphicsSource = renderEvent.getSource();
        Graphics2D textGraphics = (Graphics2D) graphicsSource;
        textGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Minor calculations
        int experienceGained = Skills.getExperience(Skill.COOKING) - startCookingXP;
        int levelsGained = Skills.getVirtualLevel(Skill.COOKING) - startCookingLevel;

        // Foreground rectangle (opaque)
        textGraphics.setColor(new Color(0, 0, 0, 0.60F));
        textGraphics.fillRect(3, 258, 513, 80);

        // Background rectangle trim (border)
        textGraphics.setColor(new Color(73, 255, 99));
        textGraphics.drawRect(3, 258, 513, 80);

        // Create a larger font size
        Font normalFont = textGraphics.getFont();
        Font scriptNameFont = normalFont.deriveFont(normalFont.getSize() * 1.5F);
        Font elapsedTimeFont = normalFont.deriveFont(normalFont.getSize() * 1.35F);
        Font versionFont = normalFont.deriveFont(normalFont.getSize() * 0.8F);
        Font lineFont = normalFont.deriveFont(normalFont.getSize() * 1.1F);

        // Script name
        textGraphics.setFont(scriptNameFont);
        textGraphics.drawString("bonBeefCooker", 20, 285);

        // Script elapsed time
        long elapsedTime = System.currentTimeMillis() - startTime;
        textGraphics.setFont(elapsedTimeFont);
        textGraphics.drawString("Time Elapsed: " + stopWatch.toElapsedString(), 20, 310);

        // Script version
        textGraphics.setFont(versionFont);
        textGraphics.drawString("Version 1.1", 22, 330);

        textGraphics.setFont(lineFont);

        // Line One
        textGraphics.drawString("Beef Collected: " + beefPickedUp, 380, 280);
        textGraphics.drawString(String.format("Levels Gained: %d (%.2f)", levelsGained, stopWatch.getHourlyRate(levelsGained)), 200, 280);

        // Line Two
        textGraphics.drawString("Beef Cooked: " + beefCooked, 380, 305);
        textGraphics.drawString(String.format("XP Gained: %d (%.2f)", experienceGained, stopWatch.getHourlyRate(experienceGained)), 200, 305);

        // Line Three
        textGraphics.drawString("Beef Burnt: " + beefBurned, 380, 330);
        textGraphics.drawString("Task: " + currentTask, 200, 330);
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
        int rawBeefItemID = 2132;
        if (itemTableEvent.getChangeType() == ItemTableEvent.ChangeType.ITEM_ADDED && itemTableEvent.getId() == rawBeefItemID) {
            beefPickedUp++;
        }
    }
}
