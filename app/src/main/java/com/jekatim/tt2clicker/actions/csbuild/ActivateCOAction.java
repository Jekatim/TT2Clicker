package com.jekatim.tt2clicker.actions.csbuild;

import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class ActivateCOAction implements Action {

    private static String TAG = "ActivateCOAction";

    private static final Coordinates COArea = new Coordinates(430, 970);

    @Override
    public void perform() {
        // click on co area
        Log.d(TAG, "Activating CO");
        AutoClickerService.instance.click(COArea.x, COArea.y);
        pause500();
    }
}
