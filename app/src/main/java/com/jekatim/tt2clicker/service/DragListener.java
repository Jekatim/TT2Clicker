package com.jekatim.tt2clicker.service;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class DragListener implements View.OnTouchListener {

    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;
    private final WindowManager.LayoutParams params;
    private final WindowManager manager;

    DragListener(WindowManager.LayoutParams params, WindowManager manager) {
        this.params = params;
        this.manager = manager;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = params.x;
                initialY = params.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                params.y = initialY + (int) (event.getRawY() - initialTouchY);
                manager.updateViewLayout(view, params);
                return true;
            default:
                return false;
        }
        return true;
    }
}
