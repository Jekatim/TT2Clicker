package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class ActivateCOAction extends ActionWithPeriod {

    private static String TAG = "ActivateCOAction";

    private final Coordinates COArea = new Coordinates(415, 980);

    public ActivateCOAction() {
        super(10); //sec
    }

    @Override
    public void perform() {
        if (!checkTimePeriodValid()) {
            return;
        }
        // click on co area
        Log.d(TAG, "Activating CO");
        AutoClickerService.instance.click(COArea.x, COArea.y);
        lastActivatedTime = System.currentTimeMillis();
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
