package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class CommonSteps {

    private static String TAG = "CommonSteps";

    private static Coordinates closePanelCoordinates = new Coordinates(1010, 1070);
    private static Coordinates closePanelUpCoordinates = new Coordinates(1010, 1070);

    public static void closePanel(ColorChecker colorChecker) {
        // click on cross to close panel
        if (colorChecker.isClosePanelButton(closePanelCoordinates.x, closePanelCoordinates.y)) {
            Log.d(TAG, "Closing panel");
            AutoClickerService.instance.click(closePanelCoordinates.x, closePanelCoordinates.y);
            pause500();
        } else {
            if (colorChecker.isClosePanelButton(closePanelUpCoordinates.x, closePanelUpCoordinates.y)) {
                Log.d(TAG, "Closing panel");
                AutoClickerService.instance.click(closePanelUpCoordinates.x, closePanelUpCoordinates.y);
                pause500();
            } else {
                Log.d(TAG, "No panel is open, skipping");
            }
        }
    }

    public static void pause2000() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pause500() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pause200() {
        try {
            Thread.sleep((long) 200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pause100() {
        try {
            Thread.sleep((long) 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pause50() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pauseOn(int msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
