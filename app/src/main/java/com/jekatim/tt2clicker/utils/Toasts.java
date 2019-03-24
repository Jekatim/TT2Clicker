package com.jekatim.tt2clicker.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class Toasts {

    private static String TAG = "Toasts";

    private static void showToast(Context context, String text, int length) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast toast = Toast.makeText(context.getApplicationContext(), text, length);
            toast.show();
        } else {
            Log.d(TAG, "show toast run in wrong thread");
        }
    }

    public static void errorToast(Context context, Throwable e) {
        shortToast(context, e.getLocalizedMessage());
    }

    public static void longToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    public static void longToast(Context context, int id) {
        longToast(context, context.getString(id));
    }

    public static void shortToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void shortToast(Context context, int id) {
        shortToast(context, context.getString(id));
    }
}
