package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class UpgradeHeroesAction extends ActionWithPeriod {

    private static String TAG = "UpgradeHeroesAction";

    private final int scrollBetweenHeroesGap = 275;//170;

    private final ColorChecker colorChecker;
    private static final Coordinates tabCoordinates = new Coordinates(200, 1900);
    private static final Coordinates upgradeLastButton = new Coordinates(900, 1500);
    private static final Coordinates upgradeFirstButton = new Coordinates(900, 1790);
    private static final Coordinates scrollStartCoordinates = new Coordinates(500, 1800);

    private final int wholeScroll = 45;
    private final int shortScroll = 6;
    private boolean isWholeScroll = true;

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

            Log.d(TAG, "Upgrading heroes to max, making " + (isWholeScroll ? "whole scroll" : "short scroll"));
            for (int i = 0; i < (isWholeScroll ? wholeScroll : shortScroll); i++) {
                AutoClickerService.instance.click(upgradeLastButton.x, upgradeLastButton.y);
                pause200();
                AutoClickerService.instance.scrollDownOn(scrollStartCoordinates.x, scrollStartCoordinates.y, scrollBetweenHeroesGap);
                pause500();
            }

            pause500();
            Log.d(TAG, "Upgrading first hero to max");
            AutoClickerService.instance.click(upgradeFirstButton.x, upgradeFirstButton.y);
            pause500();

            scrollUp();

            isWholeScroll = !isWholeScroll;
            lastActivatedTime = System.currentTimeMillis();
        } else {
            Log.d(TAG, "Missed heroes tab");
        }
    }

    private void scrollUp() {
        for (int i = 0; i < 10; i++) {
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, 1300);
            pause500();
        }
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
