package com.jekatim.tt2clicker.actions.scbuild;

import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.actions.CommonSteps;
import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class UpgradeSCSkillAction implements Action {

    private static String TAG = "UpgradeSMNeededSkillsAction";

    private final ColorChecker colorChecker;
    private static final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private static final Coordinates slideUpPanelCoordinates = new Coordinates(865, 1066);
    private static final Coordinates slideDownPanelCoordinates = new Coordinates(860, 25);
    private static final Coordinates upgradeSMButtonCoordinates = new Coordinates(765, 1365);
    private static final Coordinates upgradeSCSkillCoordinates = new Coordinates(765, 1410);

    public UpgradeSCSkillAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // go to sword master tab
        if (colorChecker.isGoToSwordMasterTab(tabCoordinates.x, tabCoordinates.y)) {
            pause200();
            Log.d(TAG, "moving to SM tab");
            AutoClickerService.instance.click(tabCoordinates.x, tabCoordinates.y);
            pause500();

            // click on upgrade SM button
            Log.d(TAG, "Upgrading sm to max");
            AutoClickerService.instance.click(upgradeSMButtonCoordinates.x, upgradeSMButtonCoordinates.y);
            pause500();

            // slide panel up
            Log.d(TAG, "sliding panel up");
            AutoClickerService.instance.click(slideUpPanelCoordinates.x, slideUpPanelCoordinates.y);
            pause500();

            // upgrade all skills to max
            Log.d(TAG, "Upgrading sc to max");
            AutoClickerService.instance.click(upgradeSCSkillCoordinates.x, upgradeSCSkillCoordinates.y);
            pause200();
            AutoClickerService.instance.click(upgradeSCSkillCoordinates.x - 65, upgradeSCSkillCoordinates.y);
            pause200();

            Log.d(TAG, "sliding panel down");
            AutoClickerService.instance.click(slideDownPanelCoordinates.x, slideDownPanelCoordinates.y);
            pause200();
        } else {
            Log.d(TAG, "Missed SM tab");
        }
    }
}
