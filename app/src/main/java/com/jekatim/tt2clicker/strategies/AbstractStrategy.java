package com.jekatim.tt2clicker.strategies;

import android.util.Log;
import android.widget.ToggleButton;

import com.jekatim.tt2clicker.actions.Action;

import java.util.Timer;

public abstract class AbstractStrategy implements Strategy {

    private static String TAG = "AbstractStrategy";

    protected Timer timer;
    protected boolean isLaunched;
    protected ToggleButton toggle;

    public AbstractStrategy(ToggleButton toggle) {
        this.toggle = toggle;
    }

    @Override
    public void stopStrategy() {
        if (isLaunched) {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }
            isLaunched = false;
            toggle.setChecked(false);
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
