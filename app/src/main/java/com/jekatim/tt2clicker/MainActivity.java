package com.jekatim.tt2clicker;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.service.FloatingClickService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private static int PERMISSION_CODE = 110;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener((it -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !Settings.canDrawOverlays(MainActivity.this)) {
                MainActivity.this.askPermission();
                //ToastsKt.shortToast(MainActivity.this, "You need System Alert Window Permission to do this");
            } else {
                MainActivity.this.serviceIntent = new Intent(MainActivity.this, FloatingClickService.class);
                MainActivity.this.startService(MainActivity.this.serviceIntent);
                MainActivity.this.onBackPressed();
            }

        }));
    }

    private boolean checkAccess() {
        String serviceId = this.getString(R.string.accessibility_service_id);
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);

        for (AccessibilityServiceInfo info : list) {
            if (info.getId().equals(serviceId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean hasPermission = this.checkAccess();
        Log.d(TAG, "has access? " + hasPermission);
        if (!hasPermission) {
            this.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            this.askPermission();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.getPackageName()));
        this.startActivityForResult(intent, PERMISSION_CODE);
    }

    @Override
    protected void onDestroy() {

        if (serviceIntent != null) {
            Log.d(TAG, "stop floating click service");
            stopService(serviceIntent);
        }

        AutoClickerService service = AutoClickerService.getAutoClickService();
        if (service != null) {
            Log.d(TAG, "stop auto click service");
            service.stopSelf();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                service.disableSelf();
            }

            AutoClickerService.setAutoClickService(null);
        }

        super.onDestroy();
    }

    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
