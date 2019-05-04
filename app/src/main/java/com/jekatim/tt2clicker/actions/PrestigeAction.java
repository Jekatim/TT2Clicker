package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.strategies.Strategy;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;
import static com.jekatim.tt2clicker.actions.CommonSteps.pauseOn;

public class PrestigeAction implements Action {

    private static String TAG = "PrestigeAction";

    private final ColorChecker colorChecker;
    private final Strategy strategy;

    private static final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private static final Coordinates prestigeButton = new Coordinates(788, 1790);
    private static final Coordinates prestigeConfirmButton = new Coordinates(425, 1480);
    private static final Coordinates prestigeConfirmButton2 = new Coordinates(620, 1280);

    public PrestigeAction(ColorChecker colorChecker, Strategy strategy) {
        this.colorChecker = colorChecker;
        this.strategy = strategy;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // go to sword master tab
        if (colorChecker.isGoToSwordMasterTab(tabCoordinates.x, tabCoordinates.y)) {
            Log.d(TAG, "moving to SM tab");
            AutoClickerService.instance.click(tabCoordinates.x, tabCoordinates.y);
            pause500();

            scrollDown();

            // click on prestige button
            if (colorChecker.isPresigeButton(prestigeButton.x, prestigeButton.y)) {
                Log.d(TAG, "clicking on prestige button");
                AutoClickerService.instance.click(prestigeButton.x, prestigeButton.y);
                pauseOn(1000);

                // click on confirm
                if (colorChecker.isPresigeConfirmButton(prestigeConfirmButton.x, prestigeConfirmButton.y)) {
                    Log.d(TAG, "Confirming prestige");
                    AutoClickerService.instance.click(prestigeConfirmButton.x, prestigeConfirmButton.y);
                    pauseOn(1000);
                    // click on confirm2
                    if (colorChecker.isPresigeConfirmButton(prestigeConfirmButton2.x, prestigeConfirmButton2.y)) {
                        Log.d(TAG, "Confirming prestige 2");
                        AutoClickerService.instance.click(prestigeConfirmButton2.x, prestigeConfirmButton2.y);
                        pauseOn(20000);
                        strategy.addAfterPrestigeActions();
                    } else {
                        Log.d(TAG, "Missed confirm prestige button 2");
                    }
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

    private void scrollDown() {
        for (int i = 0; i < 12; i++) {
            AutoClickerService.instance.scrollDown();
            pause500();
        }
    }
}
