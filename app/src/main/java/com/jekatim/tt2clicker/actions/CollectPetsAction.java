package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause2000;

public class CollectPetsAction implements Action {

    private static String TAG = "CollectPetsAction";

    private static final Coordinates petsButton = new Coordinates(70, 670);

    private final ColorChecker colorChecker;

    public CollectPetsAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        Log.d(TAG, "Clicking on collect pet area");
        AutoClickerService.instance.click(petsButton.x, petsButton.y);
        // wait until splash for 2 sec
        pause2000();
        // click several time to close popup
        for (int i = 0; i < 5; i++) {
            AutoClickerService.instance.click(petsButton.x, petsButton.y);
            pause200();
        }
    }
}
