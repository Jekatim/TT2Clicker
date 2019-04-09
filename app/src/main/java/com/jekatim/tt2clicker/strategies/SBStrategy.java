package com.jekatim.tt2clicker.strategies;

import com.jekatim.tt2clicker.actions.UpgradeShadowBookAction;
import com.jekatim.tt2clicker.settings.ClickingStrategy;
import com.jekatim.tt2clicker.settings.SettingsModel;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class SBStrategy extends RelicsStrategy {

    private static String TAG = "SBStrategy";

    public SBStrategy(SettingsModel settings, ColorChecker colorChecker) {
        super(settings, colorChecker);
    }

    @Override
    public ClickingStrategy getType() {
        return ClickingStrategy.SB_MODE;
    }

    @Override
    public void addAfterPrestigeActions() {
        super.addAfterPrestigeActions();

        oneTimeActions.add(new UpgradeShadowBookAction(colorChecker));
    }
}
