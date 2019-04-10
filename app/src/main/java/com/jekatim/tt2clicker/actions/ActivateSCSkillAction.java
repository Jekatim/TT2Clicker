package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class ActivateSCSkillAction extends ActionWithPeriod {

    private static String TAG = "ActivateSCSkillAction";

    private final ColorChecker colorChecker;
    private static final Coordinates scCoordinates = new Coordinates(1000, 1722);

    public ActivateSCSkillAction(ColorChecker colorChecker) {
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
        Log.d(TAG, "Activating shadow clone skill");
        AutoClickerService.instance.click(scCoordinates.x, scCoordinates.y);
        pause500();
        lastActivatedTime = System.currentTimeMillis();
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
