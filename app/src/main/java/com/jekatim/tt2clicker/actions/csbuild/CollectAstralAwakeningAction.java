package com.jekatim.tt2clicker.actions.csbuild;

import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.actions.CommonSteps;
import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause50;

public class CollectAstralAwakeningAction implements Action {

    private static String TAG = "CollectAstralAwakeningAction";

    private final ColorChecker colorChecker;

    // x = 40 -> 250 ; 875 -> 1045
    // y = 470 -> 980 ; 240 -> 990
    private int leftStaticY = 470;
    private int rightStaticY = 240;
    private int clicksNumber = 5;

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
        int shift = (980 - leftStaticY) / clicksNumber;
        for (int i = 0; i < clicksNumber; i++) {
            AutoClickerService.instance.click(40, leftStaticY + (i * shift));
            pause50();
            AutoClickerService.instance.click(250, leftStaticY + (i * shift));
            pause50();
        }
    }

    private void tapOnRightSide() {
        int shift = (990 - rightStaticY) / clicksNumber;
        for (int i = 0; i < clicksNumber; i++) {
            AutoClickerService.instance.click(875, rightStaticY + (i * shift));
            pause50();
            AutoClickerService.instance.click(1045, rightStaticY + (i * shift));
            pause50();
        }
    }
}
