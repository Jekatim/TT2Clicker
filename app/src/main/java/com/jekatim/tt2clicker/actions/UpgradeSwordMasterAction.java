package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class UpgradeSwordMasterAction extends ActionWithPeriod {

    private static String TAG = "UpgradeSwordMasterAction";

    private final ColorChecker colorChecker;
    private final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private final Coordinates upgradeSMButtonCoordinates = new Coordinates(765, 1365);
    private final Coordinates scrollStartCoordinates = new Coordinates(500, 1300);

    public UpgradeSwordMasterAction(ColorChecker colorChecker) {
        super(60);
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        if (!checkTimePeriodValid()) {
            return;
        }
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // go to sword master tab
        if (colorChecker.isGoToSwordMasterTab(tabCoordinates.x, tabCoordinates.y)) {
            Log.d(TAG, "moving to SM tab");
            AutoClickerService.instance.click(tabCoordinates.x, tabCoordinates.y);
            pause500();

            //scrollUp();

            // click on upgrade button
            Log.d(TAG, "Upgrading sm to max");
            AutoClickerService.instance.click(upgradeSMButtonCoordinates.x, upgradeSMButtonCoordinates.y);
            pause200();
        } else {
            Log.d(TAG, "Missed SM tab");
        }
    }

    private void scrollUp() {
        for (int i = 0; i < 5; i++) {
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause500();
        }
    }


    @Override
    protected String getTag() {
        return TAG;
    }
}
