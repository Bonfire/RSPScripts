package com.bonfire;

import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.awt.*;

@ScriptMeta(developer = "Bonfire", name = "bonScouter", desc = "A script that scouts a specific area and hops worlds, calling out any people that have specific items.", version = 1.1)
public class bonScouter extends TaskScript implements RenderListener {
    private static final Task[] TASKS = {new Hop(), new Scan()};
    private static boolean scanComplete = true;
    private static int worldsHopped;
    private static int playerScanned;
    private static int playersTargeted;
    private static String webhookURL;
    // Use the non-primitive Boolean class for null checking
    private static Boolean useWebhook = null;

    static boolean isScanComplete() {
        return scanComplete;
    }

    static void setScanComplete(boolean scanComplete) {
        bonScouter.scanComplete = scanComplete;
    }

    static void incrementWorld() {
        worldsHopped++;
    }

    static void incrementPlayersScanned() {
        playerScanned++;
    }

    static void incrementPlayersTargeted() {
        playersTargeted++;
    }

    public static String getWebhookURL() {
        return webhookURL;
    }

    public static void setWebhookURL(String webhookURL) {
        bonScouter.webhookURL = webhookURL;
    }

    public static Boolean isUseWebhook() {
        return useWebhook;
    }

    public static void setUseWebhook(boolean useWebhook) {
        bonScouter.useWebhook = useWebhook;
    }

    public void onStart() {
        Log.fine("Starting bonScouter");
        Log.fine("Showing Configuration GUI");

        // Show the GUI
        new scouterGUI().setVisible(true);

        // Start the script's tasks
        submit(TASKS);
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics graphic = renderEvent.getSource();
        graphic.drawString("Worlds Hopped: " + worldsHopped, 10, 40);
        graphic.drawString("Players Scanned: " + playerScanned, 10, 60);
        graphic.drawString("Players Targeted: " + playersTargeted, 10, 80);
    }
}
