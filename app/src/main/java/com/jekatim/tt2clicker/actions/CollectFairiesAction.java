package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class CollectFairiesAction implements Action {

    private static String TAG = "CollectFairiesAction";

    private final ColorChecker colorChecker;

    // y = 450 -> 500
    // x = 230 -> 1050

    public CollectFairiesAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        //click on horizontal line and back
        Log.d(TAG, "Tapping on fairies");

        AutoClickerService.instance.click(240, 450);
        pause();
        AutoClickerService.instance.click(320, 455);
        pause();
        AutoClickerService.instance.click(400, 460);
        pause();
        AutoClickerService.instance.click(480, 465);
        pause();
        AutoClickerService.instance.click(560, 470);
        pause();
        AutoClickerService.instance.click(640, 475);
        pause();
        AutoClickerService.instance.click(720, 480);
        pause();
        AutoClickerService.instance.click(800, 485);
        pause();
        AutoClickerService.instance.click(880, 490);
        pause();
        AutoClickerService.instance.click(960, 495);
        pause();
        AutoClickerService.instance.click(1040, 500);
        pause();


        AutoClickerService.instance.click(1040, 500);
        pause();
        AutoClickerService.instance.click(960, 495);
        pause();
        AutoClickerService.instance.click(880, 490);
        pause();
        AutoClickerService.instance.click(800, 485);
        pause();
        AutoClickerService.instance.click(720, 480);
        pause();
        AutoClickerService.instance.click(640, 475);
        pause();
        AutoClickerService.instance.click(560, 470);
        pause();
        AutoClickerService.instance.click(480, 465);
        pause();
        AutoClickerService.instance.click(400, 460);
        pause();
        AutoClickerService.instance.click(320, 455);
        pause();
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
