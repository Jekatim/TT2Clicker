package com.jekatim.tt2clicker.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.jekatim.tt2clicker.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class AutoClickerService extends AccessibilityService {

    private static String TAG = "AutoClickerService";

    private static AutoClickerService autoClickService;
    private final List events;

    public AutoClickerService() {
        this.events = new ArrayList();
    }

    public static AutoClickerService getAutoClickService() {
        return autoClickService;
    }

    public static void setAutoClickService(AutoClickerService service) {
        autoClickService = service;
    }

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
        AutoClickerService.setAutoClickService(this);
        this.startActivity((new Intent(this, MainActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public final void click(int x, int y) {
        Log.d(TAG, "click " + x + ' ' + y);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Path path = new Path();
            path.moveTo((float) x, (float) y);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            GestureDescription gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 10L, 10L)).build();
            this.dispatchGesture(gestureDescription, null, null);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "AutoClickService onUnbind");
        AutoClickerService.setAutoClickService(null);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "AutoClickService onDestroy");
        AutoClickerService.setAutoClickService(null);
        super.onDestroy();
    }


}
