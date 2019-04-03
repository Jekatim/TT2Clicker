package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class UpgradeHeroesAction implements Action {

    private static String TAG = "UpgradeHeroesAction";

    private final int scrollBetweenHeroesGap = 170;

    private final ColorChecker colorChecker;
    private final Coordinates tabCoordinates = new Coordinates(200, 1900);
    private final Coordinates upgradeLastButton = new Coordinates(765, 1535);
    private final Coordinates upgradeFirstButton = new Coordinates(765, 1790);
    private final Coordinates scrollStartCoordinates = new Coordinates(500, 1800);

    private long lastActivatedTime;
    private long period = 30; //sec

    public UpgradeHeroesAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        if (System.currentTimeMillis() - lastActivatedTime < period * 1000) {
            Log.d(TAG, "Time is not come yet, skipping");
            return;
        }
        // close tab if needed
        Log.d(TAG, "closing panel");
        CommonSteps.closePanel(colorChecker);
        // go to heroes tab
        if (colorChecker.isGoToHeroesTab(tabCoordinates.x, tabCoordinates.y)) {
            Log.d(TAG, "moving to heroes tab");
            AutoClickerService.instance.click(tabCoordinates.x, tabCoordinates.y);
            pause();

            //scroll up
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();

            // cycle through all heroes
            for (int i = 0; i < 10; i++) {
                // click on upgrade button
                while (!colorChecker.isNoUpgradeAvailable(upgradeLastButton.x, upgradeLastButton.y)) {
                    Log.d(TAG, "Upgrading hero to max");
                    AutoClickerService.instance.click(upgradeLastButton.x, upgradeLastButton.y);
                }
                AutoClickerService.instance.scrollDownOn(scrollStartCoordinates.x, scrollStartCoordinates.y, scrollBetweenHeroesGap);
                pause();
            }

            // click on upgrade button
            while (!colorChecker.isNoUpgradeAvailable(upgradeFirstButton.x, upgradeFirstButton.y)) {
                Log.d(TAG, "Upgrading hero to max");
                AutoClickerService.instance.click(upgradeFirstButton.x, upgradeFirstButton.y);
            }

            //scroll down
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

            lastActivatedTime = System.currentTimeMillis();
        } else {
            Log.d(TAG, "Missed heroes tab");
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
