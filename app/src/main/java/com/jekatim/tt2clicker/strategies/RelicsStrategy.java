package com.jekatim.tt2clicker.strategies;

import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.actions.FreeClicksAction;
import com.jekatim.tt2clicker.actions.PrestigeAction;
import com.jekatim.tt2clicker.actions.UpgradeHeroesAction;
import com.jekatim.tt2clicker.settings.ClickingStrategy;
import com.jekatim.tt2clicker.settings.SettingsModel;
import com.jekatim.tt2clicker.utils.ColorChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RelicsStrategy implements Strategy {

    private static String TAG = "RelicsStrategy";

    private final SettingsModel settings;
    private final ColorChecker colorChecker;

    private Timer timer;
    private boolean isOn = false;
    private final List<Action> actions;

    public RelicsStrategy(SettingsModel settings, ColorChecker colorChecker) {
        this.settings = settings;
        this.colorChecker = colorChecker;
        this.actions = new ArrayList<>();

        actions.add(new UpgradeHeroesAction(colorChecker));
        actions.add(new FreeClicksAction());
    }

    @Override
    public ClickingStrategy getType() {
        return ClickingStrategy.RELIC_MODE;
    }

    @Override
    public void launchStrategy() {
        if (isOn) {
            Log.d(TAG, "Already launched, skipping");
        } else {
            this.timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    control();
                }
            }, 1000, 1000);
            Log.d(TAG, "Launched");
            isOn = true;
        }
    }

    private void control() {
        for (Action action : actions) {
            action.perform();
        }
    }

    @Override
    public void stopStrategy() {
        if (isOn) {
            if (timer != null) {
                timer.cancel();
            }
            isOn = false;
            Log.d(TAG, "Stopping");
        } else {
            Log.d(TAG, "Already stopped, skipping");
        }
    }
}
