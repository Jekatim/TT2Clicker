package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class ScrollUpAfterPrestigeAction implements Action {

    private static String TAG = "ScrollUpAfterPrestigeAction";

    private final ColorChecker colorChecker;

    private static final Coordinates tabCoordinates = new Coordinates(30, 1900);
    private static final Coordinates scrollStartCoordinates = new Coordinates(500, 1300);

    public ScrollUpAfterPrestigeAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // go to sword master tab
        if (colorChecker.isGoToSwordMasterTab(tabCoordinates.x, tabCoordinates.y)) {
            Log.d(TAG, "moving to SM tab");
            AutoClickerService.instance.click(tabCoordinates.x, tabCoordinates.y);
            pause500();

            scrollUp();
        } else {
            Log.d(TAG, "Missed SM tab");
        }
    }

    private void scrollUp() {
        for (int i = 0; i < 12; i++) {
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause500();
        }
    }
}
