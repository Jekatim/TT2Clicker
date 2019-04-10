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

        // FIXME
        //readSettings();

        findViewById(R.id.applySettingsButton).setOnClickListener(v -> applySettings());
    }

    private void readSettings() {
        SettingsModel model = (SettingsModel) getIntent().getExtras().get(SETTINGS_KEY);

        EditText num = findViewById(R.id.cqTapPeriod);
        num.setText(model.getCqTapPeriod());

        EditText autoPrestige = findViewById(R.id.autoPrestigeAfter);
        autoPrestige.setText(model.getAutoPrestigeAfter());

        int id = resolveCheckedStrategy(model.getStrategy());
        RadioGroup group = findViewById(R.id.strategySwitch);
        group.check(id);
    }

    private void applySettings() {
        SettingsModel settings = new SettingsModel();

        EditText num = findViewById(R.id.cqTapPeriod);
        int tapPeriod = Integer.parseInt(num.getText().toString());
        settings.setCqTapPeriod(tapPeriod);

        EditText autoPrestige = findViewById(R.id.autoPrestigeAfter);
        int prestigePeriod = Integer.parseInt(autoPrestige.getText().toString());
        settings.setAutoPrestigeAfter(prestigePeriod);

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

    private int resolveCheckedStrategy(ClickingStrategy strategy) {
        switch (strategy) {
            case CQ_MODE:
                return R.id.cqModeSwitch;
            case SB_MODE:
                return R.id.sbModeSwitch;
            case RELIC_MODE:
                return R.id.relicModeOn;
            default:
                return R.id.cqModeSwitch;
        }
    }
}
