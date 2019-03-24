package com.jekatim.tt2clicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.jekatim.tt2clicker.service.FloatingClickService;
import com.jekatim.tt2clicker.service.SettingsModel;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static String TAG = "SettingsActivity2";
    public static String SETTINGS_KEY = "Settings_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch s = findViewById(R.id.cqModeSwitch);
        if (s != null) {
            s.setOnCheckedChangeListener(this);
        }

        findViewById(R.id.applySettingsButton).setOnClickListener(v -> applySettings());
    }

    private void applySettings() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean switchPref = sharedPref.getBoolean("cqMode", false);

        EditText num = findViewById(R.id.cqTapPeriod);
        int tapPeriod = Integer.parseInt(num.getText().toString());

        SettingsModel settings = new SettingsModel();
        settings.setCQMode(switchPref);
        settings.setCqTapPeriod(tapPeriod);

        Log.d(TAG, "Settings will be applied: " + settings.toString());

        sendMessage(settings);

        finish();
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Switch s = findViewById(R.id.cqModeSwitch);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor e = settings.edit();
        e.putBoolean("cqMode", s.isChecked());
        e.apply();
    }

    public void sendMessage(SettingsModel settings) {
        Log.d(TAG, "MainActivity.send()");
        Intent sendableIntent = new Intent(FloatingClickService.MyBroadcastReceiver.class.getName());
        sendableIntent.putExtra(SETTINGS_KEY, settings);
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendableIntent);
    }
}
