package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class ActivateHandOfMidasSkillAction implements Action {

    private static String TAG = "ActivateHandOfMidasSkillAction";

    private final ColorChecker colorChecker;
    private final Coordinates handOfMidasCoordinates = new Coordinates(450, 1722);

    public ActivateHandOfMidasSkillAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // click on HoM button
        Log.d(TAG, "Activating Hand of Midas skill");
        AutoClickerService.instance.click(handOfMidasCoordinates.x, handOfMidasCoordinates.y);
    }
}
