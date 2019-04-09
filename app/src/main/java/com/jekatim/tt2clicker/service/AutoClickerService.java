package com.jekatim.tt2clicker.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Path;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.jekatim.tt2clicker.MainActivity;

public class AutoClickerService extends AccessibilityService {

    private static String TAG = "AutoClickerService";

    public static AutoClickerService instance;

    @Override
    public void onInterrupt() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected");
        AutoClickerService.instance = this;
        this.startActivity((new Intent(this, MainActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public final void click(int x, int y) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            Path path = new Path();
            path.moveTo((float) x, (float) y);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            GestureDescription gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 10L, 10L)).build();
            this.dispatchGesture(gestureDescription, null, null);
        }
    }

    public final void scrollDown(int x, int y) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            Path path = new Path();
            path.moveTo((float) x, (float) y);
            path.lineTo((float) x, (float) y - 500);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            GestureDescription gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 10L, 500L)).build();
            this.dispatchGesture(gestureDescription, null, null);
        }
    }

    public final void scrollDownOn(int x, int y, int distance) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            Path path = new Path();
            path.moveTo((float) x, (float) y);
            path.lineTo((float) x, (float) y - distance);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            GestureDescription gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 10L, 500L)).build();
            this.dispatchGesture(gestureDescription, null, null);
        }
    }

    public final void scrollUp(int x, int y) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            Path path = new Path();
            path.moveTo((float) x, (float) y);
            path.lineTo((float) x, (float) y + 500);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            GestureDescription gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 10L, 500L)).build();
            this.dispatchGesture(gestureDescription, null, null);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "AutoClickService onUnbind");
        AutoClickerService.instance = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "AutoClickService onDestroy");
        AutoClickerService.instance = null;
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
