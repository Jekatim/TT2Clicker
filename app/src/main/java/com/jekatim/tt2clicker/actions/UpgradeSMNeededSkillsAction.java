package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class UpgradeSMNeededSkillsAction implements Action {

    private static String TAG = "UpgradeSMNeededSkillsAction";

    private final ColorChecker colorChecker;
    private final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private final Coordinates slideUpPanelCoordinates = new Coordinates(865, 1066);
    private final Coordinates slideDownPanelCoordinates = new Coordinates(865, 30);
    private final Coordinates upgradeHoMSkillCoordinates = new Coordinates(765, 900);
    private final Coordinates upgradeWCSkillCoordinates = new Coordinates(765, 1245);
    private final Coordinates scrollStartCoordinates = new Coordinates(500, 1300);

    public UpgradeSMNeededSkillsAction(ColorChecker colorChecker) {
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

            // slide panel up
            if (colorChecker.isSlidePanelUpButton(slideUpPanelCoordinates.x, slideUpPanelCoordinates.y)) {
                Log.d(TAG, "sliding panel up");
                AutoClickerService.instance.click(slideUpPanelCoordinates.x, slideUpPanelCoordinates.y);

                // upgrade needed skills to max
                if (colorChecker.isUnlockSkillButton(upgradeHoMSkillCoordinates.x, upgradeHoMSkillCoordinates.y)) {
                    Log.d(TAG, "Upgrading HoM to max");
                    AutoClickerService.instance.click(upgradeHoMSkillCoordinates.x, upgradeHoMSkillCoordinates.y);
                } else {
                    Log.d(TAG, "Missed HoM upgrade button");
                }
                if (colorChecker.isUnlockSkillButton(upgradeWCSkillCoordinates.x, upgradeWCSkillCoordinates.y)) {
                    Log.d(TAG, "Upgrading wc to max");
                    AutoClickerService.instance.click(upgradeWCSkillCoordinates.x, upgradeWCSkillCoordinates.y);
                } else {
                    Log.d(TAG, "Missed wc upgrade button");
                }

                // slide panel down
                if (colorChecker.isSlidePanelUpButton(slideDownPanelCoordinates.x, slideDownPanelCoordinates.y)) {
                    Log.d(TAG, "sliding panel down");
                    AutoClickerService.instance.click(slideDownPanelCoordinates.x, slideDownPanelCoordinates.y);
                } else {
                    Log.d(TAG, "Missed panel down button");
                }
            } else {
                Log.d(TAG, "Missed panel up button");
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
