package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.strategies.Strategy;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class CheckIfPushWithWCNeededAction implements Action {

    private static String TAG = "CheckIfPushWithWCNeededAction";
    private static int launchesCounter = 0;

    private final Coordinates fightBossCoordinates = new Coordinates(970, 80);
    private long lastActivatedTime;
    private final int period = 90; //sec

    private final ColorChecker colorChecker;
    private final Strategy strategy;

    public CheckIfPushWithWCNeededAction(ColorChecker colorChecker, Strategy strategy) {
        this.colorChecker = colorChecker;
        this.strategy = strategy;
    }

    @Override
    public void perform() {
        if (System.currentTimeMillis() - lastActivatedTime < period * 1000) {
            Log.d(TAG, "Time is not come yet, skipping");
            return;
        }
        // check if the button with boss appears
        if (colorChecker.isBossFailedArea(fightBossCoordinates.x, fightBossCoordinates.y)) {
            if (launchesCounter > 5) {
                strategy.addOneTimeAction(new PrestigeAction(colorChecker, strategy));
                launchesCounter = 0;
            } else {
                strategy.addOneTimeAction(new ActivateWCSkillAction(colorChecker));
                Log.d(TAG, "Activating boss fight");
                AutoClickerService.instance.click(fightBossCoordinates.x, fightBossCoordinates.y);
                launchesCounter++;
            }
        } else {
            Log.d(TAG, "No boss button available, skipping");
        }
        lastActivatedTime = System.currentTimeMillis();
    }
}
