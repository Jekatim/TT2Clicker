package com.jekatim.tt2clicker.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class Screenshooter {

    private static String TAG = "Screenshooter";

    public Screenshooter() {
    }

    public int getPixelColor(int x, int y) {
        try {
            Process sh = Runtime.getRuntime().exec("su", null, null);
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "img.png";
            OutputStream os = sh.getOutputStream();
            os.write(("/system/bin/screencap -p " + path).getBytes("ASCII"));
            os.flush();
            os.close();
            sh.waitFor();

            Bitmap screen = BitmapFactory.decodeFile(path);

            int pixel = screen.getPixel(x, y);

            Log.d(TAG, "Pixel Color: + " + Integer.toHexString(pixel) + " at x:" + x + " y:" + y);

            return pixel;
        } catch (IOException | InterruptedException e) {
            Log.e(TAG, "Error in screeshooter", e);
        }
        return -1;
    }
}
