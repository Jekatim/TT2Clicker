package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;

public class UpgradeSMSkillsAction implements Action {

    private static String TAG = "UpgradeSMSkillsAction";

    private final ColorChecker colorChecker;
    private final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private final Coordinates slideUpPanelCoordinates = new Coordinates(865, 1066);
    private final Coordinates slideDownPanelCoordinates = new Coordinates(865, 30);
    private final Coordinates upgradeHSSkillCoordinates = new Coordinates(765, 570);
    private final Coordinates upgradeDSSkillCoordinates = new Coordinates(765, 730);
    private final Coordinates upgradeHoMSkillCoordinates = new Coordinates(765, 900);
    private final Coordinates upgradeFSSkillCoordinates = new Coordinates(765, 1075);
    private final Coordinates upgradeWCSkillCoordinates = new Coordinates(765, 1245);
    private final Coordinates upgradeSCSkillCoordinates = new Coordinates(765, 1410);
    private final Coordinates scrollStartCoordinates = new Coordinates(500, 1300);

    public UpgradeSMSkillsAction(ColorChecker colorChecker) {
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

            // slide panel up
            Log.d(TAG, "sliding panel up");
            AutoClickerService.instance.click(slideUpPanelCoordinates.x, slideUpPanelCoordinates.y);

            // upgrade all skills to 1
            if (colorChecker.isUnlockSkillButton(upgradeHSSkillCoordinates.x, upgradeHSSkillCoordinates.y)) {
                Log.d(TAG, "Upgrading hs to 1");
                AutoClickerService.instance.click(upgradeHSSkillCoordinates.x, upgradeHSSkillCoordinates.y);
            } else {
                Log.d(TAG, "Missed hs upgrade button");
            }
            if (colorChecker.isUnlockSkillButton(upgradeDSSkillCoordinates.x, upgradeDSSkillCoordinates.y)) {
                Log.d(TAG, "Upgrading ds to 1");
                AutoClickerService.instance.click(upgradeDSSkillCoordinates.x, upgradeDSSkillCoordinates.y);
            } else {
                Log.d(TAG, "Missed ds upgrade button");
            }
            if (colorChecker.isUnlockSkillButton(upgradeHoMSkillCoordinates.x, upgradeHoMSkillCoordinates.y)) {
                Log.d(TAG, "Upgrading HoM to max");
                AutoClickerService.instance.click(upgradeHoMSkillCoordinates.x, upgradeHoMSkillCoordinates.y);
                pause200();
                AutoClickerService.instance.click(670, upgradeHoMSkillCoordinates.y);
            } else {
                Log.d(TAG, "Missed HoM upgrade button");
            }
            if (colorChecker.isUnlockSkillButton(upgradeFSSkillCoordinates.x, upgradeFSSkillCoordinates.y)) {
                Log.d(TAG, "Upgrading fs to 1");
                AutoClickerService.instance.click(upgradeFSSkillCoordinates.x, upgradeFSSkillCoordinates.y);
            } else {
                Log.d(TAG, "Missed fs upgrade button");
            }
            if (colorChecker.isUnlockSkillButton(upgradeWCSkillCoordinates.x, upgradeWCSkillCoordinates.y)) {
                Log.d(TAG, "Upgrading wc to max");
                AutoClickerService.instance.click(upgradeWCSkillCoordinates.x, upgradeWCSkillCoordinates.y);
                pause200();
                AutoClickerService.instance.click(670, upgradeWCSkillCoordinates.y);
            } else {
                Log.d(TAG, "Missed wc upgrade button");
            }
            if (colorChecker.isUnlockSkillButton(upgradeSCSkillCoordinates.x, upgradeSCSkillCoordinates.y)) {
                Log.d(TAG, "Upgrading sc to 1");
                AutoClickerService.instance.click(upgradeSCSkillCoordinates.x, upgradeSCSkillCoordinates.y);
            } else {
                Log.d(TAG, "Missed sc upgrade button");
            }

            Log.d(TAG, "sliding panel down");
            AutoClickerService.instance.click(slideDownPanelCoordinates.x, slideDownPanelCoordinates.y);
        } else {
            Log.d(TAG, "Missed SM tab");
        }
    }
}
