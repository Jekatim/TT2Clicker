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
    private boolean isOn;
    private final int clickingX = 500;
    private final int clickingY = 500;
    private final int duration = 35; //sec

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
        if (isOn) {
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
                        Log.d(TAG, "will click on  x: " + clickingX + ", y: " + clickingY);
                        AutoClickerService.instance.click(clickingX, clickingY);
                    }
                }
            }, 1000, settings.getCqTapPeriod());
            Log.d(TAG, "Launched");
            isOn = true;
        }
    }

    @Override
    public void stopStrategy() {
        if (isOn) {
            if (timer != null) {
                timer.cancel();
            }
            isOn = false;
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
}
