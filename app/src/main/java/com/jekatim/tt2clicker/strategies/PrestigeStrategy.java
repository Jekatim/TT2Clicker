package com.jekatim.tt2clicker.strategies;

import android.content.Context;
import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.actions.CollectDailyRewardAction;
import com.jekatim.tt2clicker.actions.CollectPetsAction;
import com.jekatim.tt2clicker.actions.PrestigeAction;
import com.jekatim.tt2clicker.actions.ScrollUpAfterPrestigeAction;
import com.jekatim.tt2clicker.actions.UpgradeSwordMasterAction;
import com.jekatim.tt2clicker.actions.csbuild.UpgradeSMSkillsAction;
import com.jekatim.tt2clicker.settings.ClickingStrategy;
import com.jekatim.tt2clicker.utils.ColorChecker;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class PrestigeStrategy extends AbstractStrategy {

    private static String TAG = "PrestigeStrategy";

    private final ColorChecker colorChecker;
    private final Queue<Action> oneTimeActions;

    public PrestigeStrategy(ColorChecker colorChecker, Context context) {
        super(context);
        this.colorChecker = colorChecker;
        this.oneTimeActions = new LinkedBlockingQueue<>();

        addOneTimeAction(new PrestigeAction(colorChecker, this));
    }

    @Override
    public void addAfterPrestigeActions() {
        oneTimeActions.add(new ScrollUpAfterPrestigeAction(colorChecker));
        oneTimeActions.add(new ScrollUpAfterPrestigeAction(colorChecker));
        oneTimeActions.add(new UpgradeSwordMasterAction(colorChecker));
        oneTimeActions.add(new UpgradeSMSkillsAction(colorChecker));
        oneTimeActions.add(new CollectDailyRewardAction(colorChecker));
        oneTimeActions.add(new CollectPetsAction(colorChecker));
    }

    @Override
    public ClickingStrategy getType() {
        return ClickingStrategy.PRESTIGE_MODE;
    }

    @Override
    public void launchStrategy() {
        if (isLaunched) {
            Log.d(TAG, "Already launched, skipping");
        } else {
            this.timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    control();
                }
            }, 100, 1000);
            Log.d(TAG, "Launched");
            isLaunched = true;
        }
    }

    private void control() {
        while (oneTimeActions.peek() != null) {
            if (!isLaunched) {
                return;
            }
            Action action = oneTimeActions.poll();
            action.perform();
        }
        stopStrategy();
    }

    @Override
    public void addOneTimeAction(Action action) {
        oneTimeActions.add(action);
    }
}
