package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import java.util.Arrays;
import java.util.List;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class UpgradeHeroesAction extends ActionWithPeriod {

    private static String TAG = "UpgradeHeroesAction";

    private final int scrollBetweenHeroesGap = 280;//170;

    private final ColorChecker colorChecker;
    private final Coordinates tabCoordinates = new Coordinates(200, 1900);
    private final Coordinates upgradeLastButton = new Coordinates(900, 1500);
    private final Coordinates upgradeFirstButton = new Coordinates(900, 1760);
    private final Coordinates scrollStartCoordinates = new Coordinates(500, 1800);

    private final List<Integer> scrollNumbers = Arrays.asList(37, 30, 25, 20, 15, 12, 10, 9, 8, 6, 5);
    private final int cyclesCounter = 10;
    private int currentCycle = 0;

    public UpgradeHeroesAction(ColorChecker colorChecker) {
        super(60); //sec
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        if (!checkTimePeriodValid()) {
            return;
        }
        // close tab if needed
        Log.d(TAG, "closing panel");
        CommonSteps.closePanel(colorChecker);
        // go to heroes tab
        if (colorChecker.isGoToHeroesTab(tabCoordinates.x, tabCoordinates.y)) {
            Log.d(TAG, "moving to heroes tab");
            AutoClickerService.instance.click(tabCoordinates.x, tabCoordinates.y);
            pause500();

            scrollUp();
            pause500();

            if (currentCycle < cyclesCounter) {
                currentCycle++;
            }
            // cycle through all heroes
            for (int i = 0; i < scrollNumbers.get(currentCycle); i++) {
                // click on upgrade button
                Log.d(TAG, "Upgrading hero to max");
                AutoClickerService.instance.click(upgradeLastButton.x, upgradeLastButton.y);
                pause200();
                AutoClickerService.instance.scrollDownOn(scrollStartCoordinates.x, scrollStartCoordinates.y, scrollBetweenHeroesGap);
                pause500();
            }

            // click on upgrade button
            Log.d(TAG, "Upgrading hero to max");
            AutoClickerService.instance.click(upgradeFirstButton.x, upgradeFirstButton.y);
            pause500();

            scrollUp();

            lastActivatedTime = System.currentTimeMillis();
        } else {
            Log.d(TAG, "Missed heroes tab");
        }
    }

    private void scrollUp() {
        //scroll up
        for (int i = 0; i < 10; i++) {
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause500();
        }
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
