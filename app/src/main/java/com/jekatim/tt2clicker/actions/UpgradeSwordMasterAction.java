package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

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
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause();

            // click on upgrade button
            if (colorChecker.isUnlockSkillButton(upgradeSMButtonCoordinates.x, upgradeSMButtonCoordinates.y)) {
                Log.d(TAG, "Upgrading sm to max");
                AutoClickerService.instance.click(upgradeSMButtonCoordinates.x, upgradeSMButtonCoordinates.y);
            } else {
                Log.d(TAG, "Missed upgrade button");
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
