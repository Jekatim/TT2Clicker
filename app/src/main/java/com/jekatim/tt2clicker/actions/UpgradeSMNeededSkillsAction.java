package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class UpgradeSMNeededSkillsAction extends ActionWithPeriod {

    private static String TAG = "UpgradeSMNeededSkillsAction";

    private final ColorChecker colorChecker;
    private final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private final Coordinates slideUpPanelCoordinates = new Coordinates(865, 1066);
    private final Coordinates slideDownPanelCoordinates = new Coordinates(860, 25);
    private final Coordinates upgradeHoMSkillCoordinates = new Coordinates(900, 900);
    private final Coordinates upgradeWCSkillCoordinates = new Coordinates(900, 1245);
    private final Coordinates scrollStartCoordinates = new Coordinates(500, 1300);

    public UpgradeSMNeededSkillsAction(ColorChecker colorChecker) {
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
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause500();

            // slide panel up
            Log.d(TAG, "sliding panel up");
            AutoClickerService.instance.click(slideUpPanelCoordinates.x, slideUpPanelCoordinates.y);
            pause500();

            // upgrade needed skills to max
            Log.d(TAG, "Upgrading HoM to max");
            AutoClickerService.instance.click(upgradeHoMSkillCoordinates.x, upgradeHoMSkillCoordinates.y);
            pause200();
            // for now we will try to go with one level wc till the wall
            /*Log.d(TAG, "Upgrading wc to max");
            AutoClickerService.instance.click(upgradeWCSkillCoordinates.x, upgradeWCSkillCoordinates.y);
            pause200();*/

            // slide panel down
            Log.d(TAG, "sliding panel down");
            AutoClickerService.instance.click(slideDownPanelCoordinates.x, slideDownPanelCoordinates.y);
            pause500();
        } else {
            Log.d(TAG, "Missed SM tab");
        }
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
