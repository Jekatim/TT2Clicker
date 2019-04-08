package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;

public class ActivatePetHoMAction extends ActionWithPeriod {

    private static String TAG = "ActivatePetHoMAction";

    private final Coordinates petArea = new Coordinates(640, 900);

    public ActivatePetHoMAction() {
        super(10); //sec
    }

    @Override
    public void perform() {
        if (!checkTimePeriodValid()) {
            return;
        }

        Log.d(TAG, "Activating PHoM");
        AutoClickerService.instance.click(petArea.x, petArea.y);
        lastActivatedTime = System.currentTimeMillis();
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
