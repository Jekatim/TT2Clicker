package com.jekatim.tt2clicker.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jekatim.tt2clicker.R;
import com.jekatim.tt2clicker.utils.Converters;

import java.util.Timer;
import java.util.TimerTask;

public class FloatingClickService extends Service {

    private static String TAG = "FloatingClickService";

    private WindowManager manager;
    private View view;
    private WindowManager.LayoutParams params;
    private int xForRecord;
    private int yForRecord;
    private final int[] location = new int[2];
    private int startDragDistance;
    private Timer timer;
    private boolean isOn;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.startDragDistance = Converters.dp2px(this, 10.0F);
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

        view.setOnTouchListener(new TouchAndDragListener(params, startDragDistance, this::viewOnClick, () -> manager.updateViewLayout(view, params)));
    }

    private void viewOnClick() {
        if (this.isOn) {
            if (timer != null) {
                timer.cancel();
            }
        } else {
            this.timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    view.getLocationOnScreen(location);
                    AutoClickerService.getAutoClickService().click(location[0] + view.getRight() + 10,
                            location[1] + view.getBottom() + 10);
                }
            }, 0, 10);
        }

        this.isOn = !this.isOn;
        ((TextView) view).setText(this.isOn ? "ON" : "OFF");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "FloatingClickService onDestroy");
        if (timer != null) {
            timer.cancel();
        }
        manager.removeView(view);
    }

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
}