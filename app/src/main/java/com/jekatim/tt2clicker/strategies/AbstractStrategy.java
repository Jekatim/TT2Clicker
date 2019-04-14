package com.jekatim.tt2clicker.strategies;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.service.FloatingClickService;

import java.util.Timer;

public abstract class AbstractStrategy implements Strategy {

    private static String TAG = "AbstractStrategy";
    private static String TOGGLE_STATE_KEY = "toggleStateKey";

    private final Context context;

    protected Timer timer;
    protected boolean isLaunched;

    protected AbstractStrategy(Context context) {
        this.context = context;
    }

    @Override
    public void stopStrategy() {
        if (isLaunched) {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }
            isLaunched = false;
            untoggleButton();
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

    private void untoggleButton() {
        Log.d(TAG, "AbstractStrategy.sendUntoggle()");
        Intent sendableIntent = new Intent(FloatingClickService.MyBroadcastReceiver.class.getName());
        sendableIntent.putExtra(TOGGLE_STATE_KEY, false);
        LocalBroadcastManager.getInstance(context).sendBroadcast(sendableIntent);
    }
}
