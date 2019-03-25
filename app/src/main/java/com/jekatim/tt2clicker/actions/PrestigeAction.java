package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class PrestigeAction implements Action {

    private static String TAG = "PrestigeAction";

    private final ColorChecker colorChecker;
    private final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private final Coordinates prestigeButton = new Coordinates(788, 1790);
    private final Coordinates prestigeConfirmButton = new Coordinates(425, 1480);
    private final Coordinates scrollStartCoordinates = new Coordinates(500, 1800);

    public PrestigeAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // go to sword master tab
        if (colorChecker.isGoToSwordMasterTab(tabCoordinates.x, tabCoordinates.y)) {
            Log.d(TAG, "moving to SM tab");
            AutoClickerService.instance.click(tabCoordinates.x, tabCoordinates.y);
            pause();

            // scroll down
            AutoClickerService.instance.scrollDown(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollDown(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollDown(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollDown(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollDown(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();

            // click on prestige button
            if (colorChecker.isPresigeButton(prestigeButton.x, prestigeButton.y)) {
                Log.d(TAG, "clicking on prestige button");
                AutoClickerService.instance.click(prestigeButton.x, prestigeButton.y);

                // click on confirm
                if (colorChecker.isPresigeConfirmButton(prestigeConfirmButton.x, prestigeConfirmButton.y)) {
                    Log.d(TAG, "Confirming prestige");
                    AutoClickerService.instance.click(prestigeConfirmButton.x, prestigeConfirmButton.y);
                } else {
                    Log.d(TAG, "Missed confirm prestige button");
                }
            } else {
                Log.d(TAG, "Missed prestige button");
            }
        } else {
            Log.d(TAG, "Missed SM tab");
        }
    }

    private static void pause() {
        try {
            Thread.sleep((long) 200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
