package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class ActivateWCSkillAction implements Action {

    private static String TAG = "ActivateWCSkillAction";

    private final ColorChecker colorChecker;
    private final Coordinates wcCoordinates = new Coordinates(810, 1722);

    public ActivateWCSkillAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // click on wc button
        Log.d(TAG, "Activating War cry skill");
        AutoClickerService.instance.click(wcCoordinates.x, wcCoordinates.y);
    }
}
