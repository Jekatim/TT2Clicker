package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class ActivatePetHoMAction implements Action {

    private static String TAG = "ActivatePetHoMAction";

    private final ColorChecker colorChecker;
    private final Coordinates petArea = new Coordinates(640, 900);
    private long lastActivatedTime;
    private final int skillDuration = 10; //sec

    public ActivatePetHoMAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        if (System.currentTimeMillis() - lastActivatedTime < skillDuration * 1000) {
            Log.d(TAG, "Time is not come yet, skipping");
            return;
        }
        CommonSteps.closePanel(colorChecker);

        Log.d(TAG, "Activating PHoM");
        AutoClickerService.instance.click(petArea.x, petArea.y);
        lastActivatedTime = System.currentTimeMillis();
    }
}
