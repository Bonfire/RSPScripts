package com.bonfire;

import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.TaskScript;

import java.awt.*;
import java.time.LocalTime;

@ScriptMeta(developer = "Bonfire", name = "bonGUIDesign", desc = "A test GUI designer script", version = 0.1)
public class bonGUIDesign extends TaskScript implements RenderListener {
    private long startTime = 0L;

    @Override
    public void onStart() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics graphicsSource = renderEvent.getSource();
        Graphics2D textGraphics = (Graphics2D) graphicsSource;
        textGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Foreground rectangle (opaque)
        textGraphics.setColor(new Color(0, 0, 0, 0.60F));
        textGraphics.fillRect(3, 258, 513, 80);

        // Background rectangle trim (border)
        textGraphics.setColor(new Color(255, 200, 0));
        textGraphics.drawRect(3, 258, 513, 80);

        // Create a larger font size
        Font normalFont = textGraphics.getFont();
        Font scriptNameFont = normalFont.deriveFont(normalFont.getSize() * 1.5F);
        Font elapsedTimeFont = normalFont.deriveFont(normalFont.getSize() * 1.35F);
        Font versionFont = normalFont.deriveFont(normalFont.getSize() * 0.8F);
        Font lineFont = normalFont.deriveFont(normalFont.getSize() * 1.2F);

        // Script name
        textGraphics.setFont(scriptNameFont);
        textGraphics.drawString("bonGUIDesign", 20, 285);

        // Script elapsed time
        long elapsedTime = System.currentTimeMillis() - startTime;
        textGraphics.setFont(elapsedTimeFont);
        textGraphics.drawString("Time Elapsed: " + LocalTime.ofSecondOfDay(elapsedTime / 1000).toString(), 20, 310);

        // Script version
        textGraphics.setFont(versionFont);
        textGraphics.drawString("Version 1.0", 22, 330);

        // Line One
        textGraphics.setFont(lineFont);
        textGraphics.drawString("Some Count: 1", 400, 280);

        // Line Two
        textGraphics.setFont(lineFont);
        textGraphics.drawString("Some Count: 2", 400, 305);

        // Line Three
        textGraphics.setFont(lineFont);
        textGraphics.drawString("Some Count: 3", 400, 330);
    }
}
