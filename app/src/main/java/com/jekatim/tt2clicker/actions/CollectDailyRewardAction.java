package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause2000;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class CollectDailyRewardAction implements Action {

    private static String TAG = "CollectDailyRewardAction";

    private static final Coordinates dailyRewardButton = new Coordinates(70, 420);
    private static final Coordinates dailyRewardConfirmButton = new Coordinates(400, 1260);

    private final ColorChecker colorChecker;

    public CollectDailyRewardAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        Log.d(TAG, "Clicking on daily reward area");
        AutoClickerService.instance.click(dailyRewardButton.x, dailyRewardButton.y);
        pause500();
        if (colorChecker.isDailyRewardConfirmButton(dailyRewardConfirmButton.x, dailyRewardConfirmButton.y)) {
            Log.d(TAG, "Confirming daily reward");
            AutoClickerService.instance.click(dailyRewardConfirmButton.x, dailyRewardConfirmButton.y);
            // wait until splash for 2 sec
            pause2000();
            // click several time to close popup
            for (int i = 0; i < 5; i++) {
                AutoClickerService.instance.click(dailyRewardButton.x, dailyRewardButton.y);
                pause200();
            }
        } else {
            Log.d(TAG, "Missed daily reward button");
        }
    }
}
