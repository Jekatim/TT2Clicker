package com.jekatim.tt2clicker.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Path;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.jekatim.tt2clicker.MainActivity;
import com.jekatim.tt2clicker.settings.Coordinates;

import java.util.HashMap;
import java.util.Map;

public class AutoClickerService extends AccessibilityService {

    private static String TAG = "AutoClickerService";

    public static AutoClickerService instance;

    private static final Path path = new Path();

    private GestureDescription scrollUpGestureDescription;
    private GestureDescription scrollDownGestureDescription;
    private GestureDescription scrollHeroesDownGestureDescription;
    private Coordinates clickCoordinates = new Coordinates(0, 0);
    private Map<Coordinates, GestureDescription> clickGesturesMap = new HashMap<>();

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
        Log.d(TAG, "click " + x + ' ' + y);
        clickCoordinates.reset(x, y);
        GestureDescription gestureDescription;
        if (!clickGesturesMap.containsKey(clickCoordinates)) {
            path.reset();
            path.moveTo((float) x, (float) y);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 50L, 50L)).build();
            clickGesturesMap.put(clickCoordinates, gestureDescription);
        } else {
            gestureDescription = clickGesturesMap.get(clickCoordinates);
        }

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
