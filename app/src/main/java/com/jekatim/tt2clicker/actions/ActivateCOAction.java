package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class ActivateCOAction implements Action {

    private static String TAG = "ActivateCOAction";

    private final Coordinates COArea = new Coordinates(415, 980);
    private long lastActivatedTime;
    private final int skillDuration = 10; //sec

    @Override
    public void perform() {
        if (System.currentTimeMillis() - lastActivatedTime < skillDuration * 1000) {
            Log.d(TAG, "Time is not come yet, skipping");
            return;
        }
        // click on co area
        Log.d(TAG, "Activating CO");
        AutoClickerService.instance.click(COArea.x, COArea.y);
        lastActivatedTime = System.currentTimeMillis();
    }
}
