package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class UpgradeSMSkillsAction implements Action {

    private static String TAG = "UpgradeSMSkillsAction";

    private final ColorChecker colorChecker;
    private static final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private static final Coordinates slideUpPanelCoordinates = new Coordinates(865, 1066);
    private static final Coordinates slideDownPanelCoordinates = new Coordinates(865, 30);
    private static final Coordinates upgradeHSSkillCoordinates = new Coordinates(765, 570);
    private static final Coordinates upgradeDSSkillCoordinates = new Coordinates(765, 730);
    private static final Coordinates upgradeHoMSkillCoordinates = new Coordinates(765, 900);
    private static final Coordinates upgradeFSSkillCoordinates = new Coordinates(765, 1075);
    private static final Coordinates upgradeWCSkillCoordinates = new Coordinates(765, 1245);
    private static final Coordinates upgradeSCSkillCoordinates = new Coordinates(765, 1410);
    private static final Coordinates scrollStartCoordinates = new Coordinates(500, 1300);

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
            pause500();
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause500();

            // slide panel up
            Log.d(TAG, "sliding panel up");
            AutoClickerService.instance.click(slideUpPanelCoordinates.x, slideUpPanelCoordinates.y);
            pause500();

            // upgrade all skills to 1
            Log.d(TAG, "Upgrading hs to 1");
            AutoClickerService.instance.click(upgradeHSSkillCoordinates.x, upgradeHSSkillCoordinates.y);
            pause200();
            Log.d(TAG, "Upgrading ds to 1");
            AutoClickerService.instance.click(upgradeDSSkillCoordinates.x, upgradeDSSkillCoordinates.y);
            pause200();
            Log.d(TAG, "Upgrading HoM to max");
            for (int i = 0; i < 15; i++) {
                AutoClickerService.instance.click(upgradeHoMSkillCoordinates.x, upgradeHoMSkillCoordinates.y);
                pause200();
            }
            Log.d(TAG, "Upgrading fs to 1");
            AutoClickerService.instance.click(upgradeFSSkillCoordinates.x, upgradeFSSkillCoordinates.y);
            pause200();
            Log.d(TAG, "Upgrading wc to max");
            for (int i = 0; i < 15; i++) {
                AutoClickerService.instance.click(upgradeWCSkillCoordinates.x, upgradeWCSkillCoordinates.y);
                pause200();
            }
            Log.d(TAG, "Upgrading sc to 1");
            AutoClickerService.instance.click(upgradeSCSkillCoordinates.x, upgradeSCSkillCoordinates.y);
            pause200();

            Log.d(TAG, "sliding panel down");
            AutoClickerService.instance.click(slideDownPanelCoordinates.x, slideDownPanelCoordinates.y);
            pause200();
        } else {
            Log.d(TAG, "Missed SM tab");
        }
    }
}
