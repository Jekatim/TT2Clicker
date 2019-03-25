package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class CollectAstralAwakeningAction implements Action {

    private static String TAG = "CollectAstralAwakeningAction";

    private final ColorChecker colorChecker;

    // y = 450 -> 500
    // x = 230 -> 1050

    public CollectAstralAwakeningAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        //click on left heroes column
        Log.d(TAG, "Tapping on fairies");

        AutoClickerService.instance.click(240, 450);
        pause();

    }

    private void pause() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
