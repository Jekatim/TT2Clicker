package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.strategies.Strategy;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class CheckIfPushWithWCNeededAction extends ActionWithPeriod {

    private static String TAG = "CheckIfPushWithWCNeededAction";

    private final Coordinates fightBossCoordinates = new Coordinates(970, 80);
    private final ColorChecker colorChecker;
    private final Strategy strategy;
    private int launchesCounter = 0;

    public CheckIfPushWithWCNeededAction(ColorChecker colorChecker, Strategy strategy) {
        super(90); //sec
        this.colorChecker = colorChecker;
        this.strategy = strategy;
    }

    @Override
    public void perform() {
        if (!checkTimePeriodValid()) {
            return;
        }
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // check if the button with boss appears
        if (colorChecker.isBossFailedArea(fightBossCoordinates.x, fightBossCoordinates.y)) {
            if (launchesCounter > 5) {
                strategy.addOneTimeAction(new PrestigeAction(colorChecker, strategy));
                launchesCounter = 0;
            } else {
                strategy.addOneTimeAction(new ActivateWCSkillAction(colorChecker));
                Log.d(TAG, "Activating boss fight, current count: " + launchesCounter);
                AutoClickerService.instance.click(fightBossCoordinates.x, fightBossCoordinates.y);
                pause500();
                launchesCounter++;
            }
        } else {
            Log.d(TAG, "No boss button available, skipping");
        }
        lastActivatedTime = System.currentTimeMillis();
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
