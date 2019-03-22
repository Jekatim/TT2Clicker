package com.jekatim.tt2clicker.service;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class TouchAndDragListener implements View.OnTouchListener {

    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;
    private boolean isDrag;
    private final WindowManager.LayoutParams params;
    private final int startDragDistance;
    private final Runnable onTouch;
    private final Runnable onDrag;

    TouchAndDragListener(WindowManager.LayoutParams params, int startDragDistance, Runnable onTouch, Runnable onDrag) {
        super();
        this.params = params;
        this.startDragDistance = startDragDistance;
        this.onTouch = onTouch;
        this.onDrag = onDrag;
    }

    private boolean isDragging(MotionEvent event) {
        return Math.pow((double) (event.getRawX() - this.initialTouchX), 2.0D)
                + Math.pow((double) (event.getRawY() - this.initialTouchY), 2.0D) > (double) (this.startDragDistance * this.startDragDistance);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isDrag = false;
                initialX = params.x;
                initialY = params.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!isDrag && isDragging(event)) {
                    isDrag = true;
                }
                if (!isDrag) return true;
                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                params.y = initialY + (int) (event.getRawY() - initialTouchY);
                if (onDrag != null) {
                    onDrag.run();
                }
                return true;

            case MotionEvent.ACTION_UP:
                if (!isDrag) {
                    onTouch.run();
                }
                return true;
        }
        return false;
    }
}
