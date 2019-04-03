package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;

import java.util.Timer;
import java.util.TimerTask;

public class FreeClicksAction implements Action {

    private static String TAG = "FreeClicksAction";

    private final int clickingX = 500;
    private final int clickingY = 500;
    private long launchedTime;
    private final int burstDuration = 5; //sec

    @Override
    public void perform() {
        Timer timer = new Timer();
        launchedTime = System.currentTimeMillis();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - launchedTime > burstDuration * 1000){
                    timer.cancel();
                } else {
                    Log.d(TAG, "will click on  x: " + clickingX + ", y: " + clickingY);
                    AutoClickerService.instance.click(clickingX, clickingY);
                }
            }
        }, 500, 100);
        Log.d(TAG, "Burst launched");
    }
}
