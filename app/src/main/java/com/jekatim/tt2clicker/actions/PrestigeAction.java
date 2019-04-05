package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.strategies.Strategy;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause2000;

public class PrestigeAction implements Action {

    private static String TAG = "PrestigeAction";

    private final ColorChecker colorChecker;
    private final Strategy strategy;

    private final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private final Coordinates prestigeButton = new Coordinates(788, 1790);
    private final Coordinates prestigeConfirmButton = new Coordinates(425, 1480);
    private final Coordinates scrollStartCoordinates = new Coordinates(500, 1800);

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
            pause200();

            // scroll down
            AutoClickerService.instance.scrollDown(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause200();
            AutoClickerService.instance.scrollDown(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause200();
            AutoClickerService.instance.scrollDown(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause200();
            AutoClickerService.instance.scrollDown(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause200();
            AutoClickerService.instance.scrollDown(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause200();

            // click on prestige button
            if (colorChecker.isPresigeButton(prestigeButton.x, prestigeButton.y)) {
                Log.d(TAG, "clicking on prestige button");
                AutoClickerService.instance.click(prestigeButton.x, prestigeButton.y);

                // click on confirm
                if (colorChecker.isPresigeConfirmButton(prestigeConfirmButton.x, prestigeConfirmButton.y)) {
                    Log.d(TAG, "Confirming prestige");
                    AutoClickerService.instance.click(prestigeConfirmButton.x, prestigeConfirmButton.y);
                    pause2000();
                    strategy.addAfterPrestigeActions();
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
}
