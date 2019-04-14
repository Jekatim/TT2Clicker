package com.jekatim.tt2clicker.actions.csbuild;

import android.util.Log;

import com.jekatim.tt2clicker.actions.ActionWithPeriod;
import com.jekatim.tt2clicker.actions.CommonSteps;
import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class ActivateWCSkillAction extends ActionWithPeriod {

    private static String TAG = "ActivateWCSkillAction";

    private final ColorChecker colorChecker;
    private static final Coordinates wcCoordinates = new Coordinates(810, 1722);

    public ActivateWCSkillAction(ColorChecker colorChecker) {
        super(125); //sec
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        if (!checkTimePeriodValid()) {
            return;
        }
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // click on wc button
        Log.d(TAG, "Activating War cry skill");
        AutoClickerService.instance.click(wcCoordinates.x, wcCoordinates.y);
        pause500();
        lastActivatedTime = System.currentTimeMillis();
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
