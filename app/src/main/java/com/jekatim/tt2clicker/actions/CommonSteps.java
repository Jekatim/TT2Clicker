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
            pause();
        } else {
            if (colorChecker.isClosePanelButton(closePanelUpCoordinates.x, closePanelUpCoordinates.y)) {
                Log.d(TAG, "Closing panel");
                AutoClickerService.instance.click(closePanelUpCoordinates.x, closePanelUpCoordinates.y);
                pause();
            } else {
                Log.d(TAG, "No panel is open, skipping");
            }
        }
    }

    private static void pause() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
