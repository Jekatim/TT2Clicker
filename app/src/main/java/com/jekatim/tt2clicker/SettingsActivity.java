package com.jekatim.tt2clicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jekatim.tt2clicker.service.FloatingClickService;
import com.jekatim.tt2clicker.settings.ClickingStrategy;
import com.jekatim.tt2clicker.settings.SettingsModel;

public class SettingsActivity extends AppCompatActivity {

    private static String TAG = "SettingsActivity";
    public static String SETTINGS_KEY = "Settings_message";
    public static String EXIT_KEY = "Close_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        readSettings();

        findViewById(R.id.applySettingsButton).setOnClickListener(v -> applySettings());
        findViewById(R.id.exitButton).setOnClickListener(v -> closeApplication());
    }

    private void readSettings() {
        SettingsModel model = (SettingsModel) getIntent().getExtras().get(SETTINGS_KEY);

        EditText autoPrestige = findViewById(R.id.autoPrestigeAfter);
        autoPrestige.setText(Integer.toString(model.getAutoPrestigeAfter()));

        int id = resolveCheckedStrategy(model.getStrategy());
        RadioGroup group = findViewById(R.id.strategySwitch);
        group.check(id);
    }

    private void applySettings() {
        SettingsModel settings = new SettingsModel();

        EditText autoPrestige = findViewById(R.id.autoPrestigeAfter);
        int prestigePeriod = Integer.parseInt(autoPrestige.getText().toString());
        settings.setAutoPrestigeAfter(prestigePeriod);

        ClickingStrategy strategy = resolveClickingStrategy();
        settings.setStrategy(strategy);

        CheckBox makePrestigeFlag = findViewById(R.id.makePrestigeFlag);
        boolean isMakePrestige = makePrestigeFlag.isChecked();
        settings.setMakePrestige(isMakePrestige);

        Log.d(TAG, "Settings will be applied: " + settings.toString());

        sendMessage(settings);

        finish();
    }

    private void closeApplication() {
        System.exit(1);
    }

    public void sendMessage(SettingsModel settings) {
        Intent sendableIntent = new Intent(FloatingClickService.MyBroadcastReceiver.class.getName());
        sendableIntent.putExtra(SETTINGS_KEY, settings);
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendableIntent);
    }

    private ClickingStrategy resolveClickingStrategy() {
        RadioGroup group = findViewById(R.id.strategySwitch);

        switch (group.getCheckedRadioButtonId()) {
            case R.id.pushModeSwitch:
                return ClickingStrategy.PUSH_MODE;
            case R.id.relicModeOn:
                return ClickingStrategy.RELIC_MODE;
            default:
                return ClickingStrategy.PUSH_MODE;
        }
    }

    private int resolveCheckedStrategy(ClickingStrategy strategy) {
        switch (strategy) {
            case PUSH_MODE:
                return R.id.pushModeSwitch;
            case RELIC_MODE:
                return R.id.relicModeOn;
            default:
                return R.id.pushModeSwitch;
        }
    }
}
