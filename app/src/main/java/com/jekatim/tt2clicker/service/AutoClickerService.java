package com.jekatim.tt2clicker.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Path;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.jekatim.tt2clicker.MainActivity;

public class AutoClickerService extends AccessibilityService {

    private static String TAG = "AutoClickerService";

    public static AutoClickerService instance;

    private static final Path path = new Path();

    private GestureDescription scrollUpGestureDescription;
    private GestureDescription scrollDownGestureDescription;
    private GestureDescription scrollHeroesDownGestureDescription;

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
        initGestures();
        this.startActivity((new Intent(this, MainActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public final void click(int x, int y) {
        path.reset();
        path.moveTo((float) x, (float) y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 10L, 10L)).build();
        this.dispatchGesture(gestureDescription, null, null);
    }

    public final void scrollDown() {
        this.dispatchGesture(scrollDownGestureDescription, null, null);
    }

    public final void scrollHeroesDown() {
        this.dispatchGesture(scrollHeroesDownGestureDescription, null, null);
    }

    public final void scrollUp() {
        this.dispatchGesture(scrollUpGestureDescription, null, null);
    }

    private void initGestures() {
        //scrollUp
        Path path = new Path();
        path.moveTo(500, 1300);
        path.lineTo(500, 1800);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        scrollUpGestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 10L, 500L)).build();

        //scrollDown
        path.reset();
        path.moveTo(500, 1800);
        path.lineTo(500, 1300);
        builder = new GestureDescription.Builder();
        scrollDownGestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 10L, 500L)).build();

        //scrollHeroesDown
        path.reset();
        path.moveTo(500, 1800);
        path.lineTo(500, 1525);
        builder = new GestureDescription.Builder();
        scrollHeroesDownGestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 10L, 500L)).build();
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
