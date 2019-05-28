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

            return screen != null ? screen.getPixel(x, y) : -1;
        } catch (IOException | InterruptedException e) {
            Log.e(TAG, "Error in screenshooter", e);
        }
        return -1;
    }
}
