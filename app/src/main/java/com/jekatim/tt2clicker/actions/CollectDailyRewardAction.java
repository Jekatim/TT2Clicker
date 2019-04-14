package com.jekatim.tt2clicker.actions;

import com.jekatim.tt2clicker.utils.ColorChecker;

public class CollectDailyRewardAction implements Action {

    private static String TAG = "CollectDailyRewardAction";

    private final ColorChecker colorChecker;

    public CollectDailyRewardAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // go to sword master tab
    }
}
