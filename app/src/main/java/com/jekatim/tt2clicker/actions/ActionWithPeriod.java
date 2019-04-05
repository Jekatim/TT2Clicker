package com.jekatim.tt2clicker.actions;

import android.util.Log;

public abstract class ActionWithPeriod implements Action {

    private final int period;

    long lastActivatedTime;

    ActionWithPeriod(int period) {
        this.period = period;
    }

    boolean checkTimePeriodValid() {
        if (System.currentTimeMillis() - lastActivatedTime < period * 1000) {
            Log.d(getTag(), "Time is not come yet, skipping");
            return false;
        } else {
            return true;
        }
    }

    protected abstract String getTag();
}
