package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class ActivatePetHoMAction implements Action {

    private static String TAG = "ActivatePetHoMAction";

    private final Coordinates petArea = new Coordinates(640, 900);

    @Override
    public void perform() {
        Log.d(TAG, "Activating PHoM");
        AutoClickerService.instance.click(petArea.x, petArea.y);
        pause500();
    }
}
