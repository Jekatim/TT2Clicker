package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class UpgradeHeroesAction extends ActionWithPeriod {

    private static String TAG = "UpgradeHeroesAction";

    private final ColorChecker colorChecker;
    private static final Coordinates tabCoordinates = new Coordinates(200, 1900);
    private static final Coordinates upgradeLastButton = new Coordinates(900, 1500);

    private final int scroll = 6;

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
        CommonSteps.closePanel(colorChecker);
        // go to heroes tab
        if (colorChecker.isGoToHeroesTab(tabCoordinates.x, tabCoordinates.y)) {
            Log.d(TAG, "moving to heroes tab");
            AutoClickerService.instance.click(tabCoordinates.x, tabCoordinates.y);
            pause500();

            scrollUp();
            pause500();

            Log.d(TAG, "Upgrading heroes to max");
            for (int i = 0; i < scroll; i++) {
                AutoClickerService.instance.click(upgradeLastButton.x, upgradeLastButton.y);
                pause200();
                AutoClickerService.instance.scrollHeroesDown();
                pause500();
            }

            scrollUp();

            lastActivatedTime = System.currentTimeMillis();
        } else {
            Log.d(TAG, "Missed heroes tab");
        }
    }

    private void scrollUp() {
        for (int i = 0; i < 4; i++) {
            AutoClickerService.instance.scrollUp();
            pause500();
        }
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
