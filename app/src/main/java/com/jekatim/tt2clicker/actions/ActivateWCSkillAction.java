package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class ActivateWCSkillAction implements Action {

    private static String TAG = "ActivateWCSkillAction";

    private final ColorChecker colorChecker;
    private final Coordinates wcCoordinates = new Coordinates(810, 1722);
    private long lastActivatedTime;
    private final int skillDuration = 65; //sec

    public ActivateWCSkillAction(ColorChecker colorChecker) {
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
        // click on wc button
        Log.d(TAG, "Activating War cry skill");
        AutoClickerService.instance.click(wcCoordinates.x, wcCoordinates.y);
        lastActivatedTime = System.currentTimeMillis();
    }
}
