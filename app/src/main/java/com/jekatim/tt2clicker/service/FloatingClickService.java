package com.jekatim.tt2clicker.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.jekatim.tt2clicker.R;
import com.jekatim.tt2clicker.SettingsActivity;
import com.jekatim.tt2clicker.utils.Toasts;

import java.util.Timer;
import java.util.TimerTask;

import static com.jekatim.tt2clicker.SettingsActivity.SETTINGS_KEY;

public class FloatingClickService extends Service {

    private static String TAG = "FloatingClickService";

    private WindowManager manager;
    private View view;
    private WindowManager.LayoutParams params;
    private int xForRecord;
    private int yForRecord;
    private final int[] location = new int[2];
    private Timer timer;
    private boolean isOn;
    private SettingsModel settings;

    @Override
    public void onCreate() {
        super.onCreate();

        settings = new SettingsModel();

        this.view = LayoutInflater.from(this).inflate(R.layout.widget, null);

        int overlayParam = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;

        this.params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                overlayParam,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        this.manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.addView(view, params);

        MyBroadcastReceiver handler = new MyBroadcastReceiver();
        IntentFilter receiveFilter = new IntentFilter(handler.getClass().getName());
        LocalBroadcastManager.getInstance(this).registerReceiver(handler, receiveFilter);

        view.setOnTouchListener(new DragListener(params, () -> manager.updateViewLayout(view, params)));
        view.findViewById(R.id.toggleButton).setOnClickListener(v -> {
            if (isOn) {
                stopMachine();
            } else {
                launchMachine();
            }
        });
        view.findViewById(R.id.settingsButton).setOnClickListener(v -> {
            stopMachine();
            Intent launchSettings = new Intent(this, SettingsActivity.class);
            launchSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launchSettings);
        });
    }

    private void launchMachine() {
        if (isOn) {
            Log.d(TAG, "Already launched, skipping");
        } else {
            boolean isServiceRunning = isMyServiceRunning(AutoClickerService.class);
            Log.d(TAG, "AutoClickerService is running? :" + isServiceRunning);
            if (isServiceRunning) {
                this.timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        view.getLocationOnScreen(location);
                        Log.d(TAG, "will click on  x: " + location[0] + view.getRight() + 100 + ", y: " + location[1] + view.getBottom() + 100);
                        AutoClickerService.instance.click(location[0] + view.getRight() + 100,
                                location[1] + view.getBottom() + 100);
                    }
                }, 100, settings.getCqTapPeriod());
                Log.d(TAG, "Launched");
                isOn = true;
            } else {
                Log.d(TAG, "Service is stopped, skipping");
            }
        }
    }

    private void stopMachine() {
        if (isOn) {
            if (timer != null) {
                timer.cancel();
            }
            isOn = false;
        } else {
            Log.d(TAG, "Already stopped, skipping");
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
        if (timer != null) {
            timer.cancel();
        }
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

    public void onSettingsChange(SettingsModel newSettings) {
        settings.setCQMode(newSettings.isCQMode());
        settings.setCqTapPeriod(newSettings.getCqTapPeriod());
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "MyBroadcastReceiver() {...}.onReceive()");
            Toasts.longToast(FloatingClickService.this, "Message received");
            SettingsModel model = (SettingsModel) intent.getExtras().get(SETTINGS_KEY);
            onSettingsChange(model);
        }
    }
}