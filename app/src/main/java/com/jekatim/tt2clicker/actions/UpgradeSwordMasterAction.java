package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;

public class UpgradeSwordMasterAction implements Action {

    private static String TAG = "UpgradeSwordMasterAction";

    private final ColorChecker colorChecker;
    private final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private final Coordinates upgradeSMButtonCoordinates = new Coordinates(765, 1365);
    private final Coordinates scrollStartCoordinates = new Coordinates(500, 1300);

    public UpgradeSwordMasterAction(ColorChecker colorChecker) {
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
            pause200();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause200();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause200();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause200();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause200();

            // click on upgrade button
            Log.d(TAG, "Upgrading sm to max");
            AutoClickerService.instance.click(upgradeSMButtonCoordinates.x, upgradeSMButtonCoordinates.y);
            pause200();
        } else {
            Log.d(TAG, "Missed SM tab");
        }
    }


}
