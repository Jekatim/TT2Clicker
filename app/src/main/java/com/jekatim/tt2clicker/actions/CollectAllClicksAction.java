package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause200;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause50;
import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class CollectAllClicksAction implements Action {

    private static String TAG = "CollectAllClicksAction";

    private final Coordinates COArea = new Coordinates(430, 970);
    private final Coordinates petArea = new Coordinates(640, 900);

    @Override
    public void perform() {

        Log.d(TAG, "Activating CO");
        AutoClickerService.instance.click(COArea.x, COArea.y);
        pause500();

        Log.d(TAG, "Activating PHoM");
        AutoClickerService.instance.click(petArea.x, petArea.y);
        pause500();

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
