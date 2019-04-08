package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;
import static com.jekatim.tt2clicker.actions.CommonSteps.pauseOn;

public class FreeClicksAction implements Action {

    private static String TAG = "FreeClicksAction";

    private final int clickingX = 500;
    private final int clickingY = 700;
    private final int burstDuration = 5; //sec
    private final int burstPeriod = 50; //msec

    @Override
    public void perform() {
        Log.d(TAG, "Free clicks fire");
        int numberOfTaps = (1000 / burstPeriod) * burstDuration;

        pause500();
        for (int i = 0; i < numberOfTaps; i++) {
            AutoClickerService.instance.click(clickingX, clickingY);
            pauseOn(burstPeriod);
        }
    }
}
