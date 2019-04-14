package com.jekatim.tt2clicker.actions.csbuild;

import android.util.Log;

import com.jekatim.tt2clicker.actions.ActionWithPeriod;
import com.jekatim.tt2clicker.actions.CommonSteps;
import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class ActivateHandOfMidasSkillAction extends ActionWithPeriod {

    private static String TAG = "ActivateHandOfMidasSkillAction";

    private final ColorChecker colorChecker;
    private static final Coordinates handOfMidasCoordinates = new Coordinates(450, 1722);

    public ActivateHandOfMidasSkillAction(ColorChecker colorChecker) {
        super(65); //sec
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        if (!checkTimePeriodValid()) {
            return;
        }
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // click on HoM button
        Log.d(TAG, "Activating Hand of Midas skill");
        AutoClickerService.instance.click(handOfMidasCoordinates.x, handOfMidasCoordinates.y);
        lastActivatedTime = System.currentTimeMillis();
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
