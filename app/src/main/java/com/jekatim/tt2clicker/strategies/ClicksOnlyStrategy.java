package com.jekatim.tt2clicker.strategies;

import android.content.Context;
import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.actions.CollectAllClicksAction;
import com.jekatim.tt2clicker.actions.csbuild.ActivateSupportSkillsAction;
import com.jekatim.tt2clicker.settings.ClickingStrategy;
import com.jekatim.tt2clicker.utils.ColorChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClicksOnlyStrategy extends AbstractStrategy {

    private static String TAG = "ClicksOnlyStrategy";

    private final ColorChecker colorChecker;
    private final List<Action> fastCycledActions;

    public ClicksOnlyStrategy(ColorChecker colorChecker, Context context) {
        super(context);
        this.colorChecker = colorChecker;
        this.fastCycledActions = new ArrayList<>();

        fillCycledAction();
    }

    private void fillCycledAction() {
        fastCycledActions.add(new CollectAllClicksAction());
        fastCycledActions.add(new ActivateSupportSkillsAction(colorChecker));
    }

    @Override
    public ClickingStrategy getType() {
        return ClickingStrategy.CLICKS_MODE;
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
        for (Action fastAction : fastCycledActions) {
            if (!isLaunched) {
                return;
            }
            fastAction.perform();
        }
    }
}
