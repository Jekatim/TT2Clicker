package com.jekatim.tt2clicker.actions.unused;

import android.util.Log;

import com.jekatim.tt2clicker.actions.ActionWithPeriod;
import com.jekatim.tt2clicker.actions.CommonSteps;
import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class ActivateFSSkillAction extends ActionWithPeriod {

    private static String TAG = "ActivateFSSkillAction";

    private final ColorChecker colorChecker;
    private static final Coordinates fsCoordinates = new Coordinates(620, 1722);

    public ActivateFSSkillAction(ColorChecker colorChecker) {
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
        // click on sc button
        Log.d(TAG, "Activating fire sword skill");
        AutoClickerService.instance.click(fsCoordinates.x, fsCoordinates.y);
        pause500();
        lastActivatedTime = System.currentTimeMillis();
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
