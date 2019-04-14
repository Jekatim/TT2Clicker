package com.jekatim.tt2clicker.strategies;

import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.ClickingStrategy;
import com.jekatim.tt2clicker.settings.SettingsModel;

import java.util.Timer;
import java.util.TimerTask;

public class CQStrategy implements Strategy {

    private static String TAG = "CQStrategy";

    private Timer timer;
    private boolean isLaunched;
    private final int clickingX = 500;
    private final int clickingY = 700;
    private final int duration = 35; //sec
    private final int tapPeriod = 50; //msec

    private final SettingsModel settings;

    private long launchedTime;

    public CQStrategy(SettingsModel settings) {
        this.settings = settings;
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

    @Override
    public void stopStrategy() {
        if (isLaunched) {
            if (timer != null) {
                timer.cancel();
            }
            isLaunched = false;
        } else {
            Log.d(TAG, "Already stopped, skipping");
        }
    }

    @Override
    public void addOneTimeAction(Action action) {
        // NO_OP
    }

    @Override
    public void addAfterPrestigeActions() {
        // NO_OP
    }

    @Override
    public boolean isLaunched() {
        return isLaunched;
    }
}
