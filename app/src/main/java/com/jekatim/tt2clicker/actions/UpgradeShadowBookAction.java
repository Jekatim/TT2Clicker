package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class UpgradeShadowBookAction implements Action {

    private static String TAG = "UpgradeShadowBookAction";

    private final ColorChecker colorChecker;
    private final Coordinates tabCoordinates = new Coordinates(740, 1900);
    private final Coordinates bookOfShadowsCoordinates = new Coordinates(800, 1536);
    private final Coordinates scrollStartCoordinates = new Coordinates(500, 1300);

    public UpgradeShadowBookAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // go to artifacts tab
        if (colorChecker.isGoToArtifactsTab(tabCoordinates.x, tabCoordinates.y)) {
            Log.d(TAG, "moving to artifacts tab");
            AutoClickerService.instance.click(tabCoordinates.x, tabCoordinates.y);
            pause200();

            scrollUp();

            // click on upgrade button
            if (colorChecker.isArtifactButton(bookOfShadowsCoordinates.x, bookOfShadowsCoordinates.y)) {
                Log.d(TAG, "Upgrading BoS to max");
                AutoClickerService.instance.click(bookOfShadowsCoordinates.x, bookOfShadowsCoordinates.y);
                pause200();
                AutoClickerService.instance.click(bookOfShadowsCoordinates.x, bookOfShadowsCoordinates.y);
                pause200();
                AutoClickerService.instance.click(bookOfShadowsCoordinates.x, bookOfShadowsCoordinates.y);
                pause200();
                AutoClickerService.instance.click(bookOfShadowsCoordinates.x, bookOfShadowsCoordinates.y);
                pause200();
                AutoClickerService.instance.click(bookOfShadowsCoordinates.x, bookOfShadowsCoordinates.y);
                pause200();
                AutoClickerService.instance.click(bookOfShadowsCoordinates.x, bookOfShadowsCoordinates.y);
                pause200();
            } else {
                Log.d(TAG, "Missed upgrade button");
            }
        } else {
            Log.d(TAG, "Missed artifact tab");
        }
    }

    private void scrollUp() {
        for (int i = 0; i < 12; i++) {
            AutoClickerService.instance.scrollUp(scrollStartCoordinates.x, scrollStartCoordinates.y);
            pause500();
        }
    }
}
