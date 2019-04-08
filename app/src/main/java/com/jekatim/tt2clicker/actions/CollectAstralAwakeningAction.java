package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause50;

public class CollectAstralAwakeningAction implements Action {

    private static String TAG = "CollectAstralAwakeningAction";

    private final ColorChecker colorChecker;

    // x = 40 -> 250 ; 875 -> 1045
    // y = 470 -> 980 ; 240 -> 990

    public CollectAstralAwakeningAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        //click on left heroes column
        Log.d(TAG, "Tapping on astral awakening");

        for (int i = 0; i < 3; i++) {
            tapOnLeftSide();
            pause200();
            tapOnRightSide();
            pause200();
        }
    }

    private void tapOnLeftSide() {
        int staticY = 470;
        for (int i = 0; i < 10; i++) {
            AutoClickerService.instance.click(40, staticY + (i * 48));
            pause50();
            AutoClickerService.instance.click(250, staticY + (i * 48));
            pause50();
        }
    }

    private void tapOnRightSide() {
        int staticY = 240;
        for (int i = 0; i < 15; i++) {
            AutoClickerService.instance.click(875, staticY + (i * 50));
            pause50();
            AutoClickerService.instance.click(1045, staticY + (i * 50));
            pause50();
        }
    }
}
