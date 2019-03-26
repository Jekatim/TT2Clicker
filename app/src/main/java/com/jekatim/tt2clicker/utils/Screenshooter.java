package com.jekatim.tt2clicker.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

public class Screenshooter {

    private static String TAG = "Screenshooter";

    private View view;

    public Screenshooter(View view) {
        this.view = view;
    }

    public int getPixelColor(int x, int y) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        int pixel = bitmap.getPixel(x, y);

        Log.d(TAG, "Pixel Color: + " + Integer.toHexString(pixel) + " at x:" + x + " y:" + y);

        return pixel;
    }
}
