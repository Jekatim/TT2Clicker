package com.jekatim.tt2clicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jekatim.tt2clicker.service.FloatingClickService;
import com.jekatim.tt2clicker.settings.ClickingStrategy;
import com.jekatim.tt2clicker.settings.SettingsModel;

public class SettingsActivity extends AppCompatActivity {

    private static String TAG = "SettingsActivity";
    public static String SETTINGS_KEY = "Settings_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.applySettingsButton).setOnClickListener(v -> applySettings());
    }

    private void applySettings() {
        SettingsModel settings = new SettingsModel();

        EditText num = findViewById(R.id.cqTapPeriod);
        int tapPeriod = Integer.parseInt(num.getText().toString());
        settings.setCqTapPeriod(tapPeriod);

        EditText ind = findViewById(R.id.heroesScrollStartIndex);
        int startIndex = Integer.parseInt(ind.getText().toString());
        settings.setHeroesScrollStartIndex(startIndex);

        ClickingStrategy strategy = resolveClickingStrategy();
        settings.setStrategy(strategy);

        Log.d(TAG, "Settings will be applied: " + settings.toString());

        sendMessage(settings);

        finish();
    }

    public void sendMessage(SettingsModel settings) {
        Log.d(TAG, "MainActivity.send()");
        Intent sendableIntent = new Intent(FloatingClickService.MyBroadcastReceiver.class.getName());
        sendableIntent.putExtra(SETTINGS_KEY, settings);
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendableIntent);
    }

    private ClickingStrategy resolveClickingStrategy() {
        RadioGroup group = findViewById(R.id.strategySwitch);

        switch (group.getCheckedRadioButtonId()) {
            case R.id.cqModeSwitch:
                return ClickingStrategy.CQ_MODE;
            case R.id.sbModeSwitch:
                return ClickingStrategy.SB_MODE;
            case R.id.relicModeOn:
                return ClickingStrategy.RELIC_MODE;
            default:
                return ClickingStrategy.CQ_MODE;
        }
    }
}
