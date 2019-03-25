package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class ActivateSCSkillAction implements Action {

    private static String TAG = "ActivateSCSkillAction";

    private final ColorChecker colorChecker;
    private final Coordinates scCoordinates = new Coordinates(1000, 1722);
    private long lastActivatedTime;
    private final int skillDuration = 125; //sec

    public ActivateSCSkillAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        if (System.currentTimeMillis() - lastActivatedTime < skillDuration * 1000) {
            Log.d(TAG, "Time is not come yet, skipping");
            return;
        }
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // click on sc button
        Log.d(TAG, "Activating  skill");
        AutoClickerService.instance.click(scCoordinates.x, scCoordinates.y);
        lastActivatedTime = System.currentTimeMillis();
    }
}
