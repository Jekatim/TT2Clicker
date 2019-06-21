package com.jekatim.tt2clicker.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ToggleButton;

import com.jekatim.tt2clicker.R;
import com.jekatim.tt2clicker.SettingsActivity;
import com.jekatim.tt2clicker.settings.SettingsModel;
import com.jekatim.tt2clicker.strategies.ClicksOnlyStrategy;
import com.jekatim.tt2clicker.strategies.PrestigeStrategy;
import com.jekatim.tt2clicker.strategies.PushStrategy;
import com.jekatim.tt2clicker.strategies.Strategy;
import com.jekatim.tt2clicker.utils.ColorChecker;
import com.jekatim.tt2clicker.utils.Screenshooter;
import com.jekatim.tt2clicker.utils.Toasts;

import static com.jekatim.tt2clicker.SettingsActivity.SETTINGS_KEY;

public class FloatingClickService extends Service {

    private static String TAG = "FloatingClickService";

    private WindowManager manager;
    private View view;
    private WindowManager.LayoutParams params;
    private int xForRecord;
    private int yForRecord;
    private SettingsModel settings;
    private Strategy strategy;
    private ColorChecker colorChecker;
    private ToggleButton toggle;

    @Override
    public void onCreate() {
        super.onCreate();

        this.view = View.inflate(this, R.layout.widget, null);
        this.colorChecker = new ColorChecker(new Screenshooter(), this);

        this.params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        this.manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.addView(view, params);

        MyBroadcastReceiver handler = new MyBroadcastReceiver();
        IntentFilter receiveFilter = new IntentFilter(handler.getClass().getName());
        LocalBroadcastManager.getInstance(this).registerReceiver(handler, receiveFilter);

        view.setOnTouchListener(new DragListener(params, manager));
        toggle = view.findViewById(R.id.toggleButton);
        toggle.setTextOn("On");
        toggle.setTextOff("Off");

        settings = new SettingsModel();
        strategy = new PushStrategy(settings, colorChecker, this);

        toggle.setOnClickListener(v -> {
            if (strategy.isLaunched()) {
                strategy.stopStrategy();
            } else {
                startNewStrategy();
            }
        });
        view.findViewById(R.id.settingsButton).setOnClickListener(v -> {
            strategy.stopStrategy();
            Intent launchSettings = new Intent(this, SettingsActivity.class);
            launchSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchSettings.putExtra(SETTINGS_KEY, settings);
            startActivity(launchSettings);
        });
    }

    private void startNewStrategy() {
        boolean isServiceRunning = isMyServiceRunning(AutoClickerService.class);
        Log.d(TAG, "AutoClickerService is running? :" + isServiceRunning);
        if (isServiceRunning) {
            switch (settings.getStrategy()) {
                case PUSH_MODE:
                    strategy = new PushStrategy(settings, colorChecker, this);
                    strategy.launchStrategy();
                    break;
                case CLICKS_MODE:
                    strategy = new ClicksOnlyStrategy(colorChecker, this);
                    strategy.launchStrategy();
                    break;
                case PRESTIGE_MODE:
                    strategy = new PrestigeStrategy(colorChecker, this);
                    strategy.launchStrategy();
                    break;
                default:
                    Log.d(TAG, "Unknown strategy");
                    break;
            }
        } else {
            Log.d(TAG, "Service is stopped, skipping");
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "FloatingClickService onDestroy");
        manager.removeView(view);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "FloatingClickService onConfigurationChanged");

        int x = params.x;
        int y = params.y;
        params.x = xForRecord;
        params.y = yForRecord;
        xForRecord = x;
        yForRecord = y;

        manager.updateViewLayout(view, params);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void onSettingsChanged(SettingsModel newSettings) {
        settings.setStrategy(newSettings.getStrategy());
        settings.setAutoPrestigeAfter(newSettings.getAutoPrestigeAfter());
        settings.setMakePrestige(newSettings.isMakePrestige());
    }

    private void onButtonUntoggle() {
        if (toggle != null) {
            toggle.setChecked(false);
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "MyBroadcastReceiver() {...}.onReceive()");
            SettingsModel model = (SettingsModel) intent.getExtras().get(SETTINGS_KEY);
            if (model != null) {
                Toasts.longToast(FloatingClickService.this, "Settings received");
                onSettingsChanged(model);
            } else {
                Toasts.longToast(FloatingClickService.this, "Untoggle received");
                onButtonUntoggle();
            }
        }
    }
}