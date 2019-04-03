package com.jekatim.tt2clicker;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.service.FloatingClickService;
import com.jekatim.tt2clicker.utils.Toasts;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private static int PERMISSION_CODE_OVERLAY = 110;
    private static int PERMISSION_CODE_MEMORY = 111;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        checkPermissions();
    }

    private void launchService() {
        findViewById(R.id.startButton).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !Settings.canDrawOverlays(MainActivity.this)) {
                MainActivity.this.askPermission();
                Toasts.shortToast(MainActivity.this, "You need System Alert Window Permission to do this");
            } else {
                MainActivity.this.serviceIntent = new Intent(MainActivity.this, FloatingClickService.class);
                MainActivity.this.startService(MainActivity.this.serviceIntent);
                MainActivity.this.onBackPressed();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE_MEMORY) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        launchService();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE_MEMORY);
                    }
                }
            }
        }
    }

    // To check if service is enabled
    private boolean isAccessibilitySettingsOn() {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + AutoClickerService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean hasPermission = isAccessibilitySettingsOn();
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
        this.startActivityForResult(intent, PERMISSION_CODE_OVERLAY);
    }

    @Override
    protected void onDestroy() {

        if (serviceIntent != null) {
            Log.d(TAG, "stop floating click service");
            stopService(serviceIntent);
        }

        AutoClickerService service = AutoClickerService.instance;
        if (service != null) {
            Log.d(TAG, "stop auto click service");
            service.stopSelf();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                service.disableSelf();
            }

            AutoClickerService.instance = null;
        }

        super.onDestroy();
    }

    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_CODE_MEMORY);
        } else {
            launchService();
        }
    }
}
