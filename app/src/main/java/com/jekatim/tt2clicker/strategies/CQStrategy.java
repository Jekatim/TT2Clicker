package com.jekatim.tt2clicker.strategies;

import android.content.Context;
import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.ClickingStrategy;

import java.util.Timer;
import java.util.TimerTask;

public class CQStrategy extends AbstractStrategy {

    private static String TAG = "CQStrategy";

    private final int clickingX = 500;
    private final int clickingY = 700;
    private final int duration = 35; //sec
    private final int tapPeriod = 50; //msec

    private long launchedTime;

    public CQStrategy(Context context) {
        super(context);
    }

    @Override
    public ClickingStrategy getType() {
        return ClickingStrategy.CQ_MODE;
    }

    @Override
    public void launchStrategy() {
        if (isLaunched) {
            Log.d(TAG, "Already launched, skipping");
        } else {
            this.timer = new Timer();
            launchedTime = System.currentTimeMillis();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - launchedTime > duration * 1000){
                        stopStrategy();
                    } else {
                        AutoClickerService.instance.click(clickingX, clickingY);
                    }
                }
            }, 1000, tapPeriod);
            Log.d(TAG, "Launched");
            isLaunched = true;
        }
    }
}
