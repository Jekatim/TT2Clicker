package com.jekatim.tt2clicker.strategies;

import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.actions.ActivateSCSkillAction;
import com.jekatim.tt2clicker.actions.CheckIfPushWithWCNeededAction;
import com.jekatim.tt2clicker.actions.CollectAllClicksAction;
import com.jekatim.tt2clicker.actions.PickUpEquipmentAction;
import com.jekatim.tt2clicker.actions.ScrollUpAfterPrestigeAction;
import com.jekatim.tt2clicker.actions.UpgradeHeroesAction;
import com.jekatim.tt2clicker.actions.UpgradeSMNeededSkillsAction;
import com.jekatim.tt2clicker.actions.UpgradeSMSkillsAction;
import com.jekatim.tt2clicker.actions.UpgradeSwordMasterAction;
import com.jekatim.tt2clicker.settings.ClickingStrategy;
import com.jekatim.tt2clicker.settings.SettingsModel;
import com.jekatim.tt2clicker.utils.ColorChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class RelicsStrategy implements Strategy {

    private static String TAG = "RelicsStrategy";

    private final SettingsModel settings;
    protected final ColorChecker colorChecker;

    private Timer timer;
    private boolean isOn = false;
    private final List<Action> slowCycledAction;
    private final List<Action> fastCycledActions;
    protected final Queue<Action> oneTimeActions;

    public RelicsStrategy(SettingsModel settings, ColorChecker colorChecker) {
        this.settings = settings;
        this.colorChecker = colorChecker;
        this.slowCycledAction = new ArrayList<>();
        this.fastCycledActions = new ArrayList<>();
        this.oneTimeActions = new LinkedBlockingQueue<>();

        fillCycledAction();
    }

    @Override
    public void addAfterPrestigeActions() {
        oneTimeActions.add(new ScrollUpAfterPrestigeAction(colorChecker));
        oneTimeActions.add(new UpgradeSwordMasterAction(colorChecker));
        oneTimeActions.add(new UpgradeSMSkillsAction(colorChecker));

        slowCycledAction.clear();
        fastCycledActions.clear();

        fillCycledAction();
    }

    private void fillCycledAction() {
        slowCycledAction.add(new UpgradeHeroesAction(colorChecker));
        slowCycledAction.add(new UpgradeSwordMasterAction(colorChecker));
        slowCycledAction.add(new UpgradeSMNeededSkillsAction(colorChecker));
        slowCycledAction.add(new PickUpEquipmentAction(colorChecker));
        slowCycledAction.add(new CheckIfPushWithWCNeededAction(colorChecker, this, settings.getAutoPrestigeAfter()));

        /*********************************************************/

        fastCycledActions.add(new CollectAllClicksAction());
        fastCycledActions.add(new ActivateSCSkillAction(colorChecker));
    }

    @Override
    public ClickingStrategy getType() {
        return ClickingStrategy.RELIC_MODE;
    }

    @Override
    public void launchStrategy() {
        if (isOn) {
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
            isOn = true;
        }
    }

    private void control() {
        while (oneTimeActions.peek() != null) {
            Action action = oneTimeActions.poll();
            action.perform();
        }
        for (Action slowAction : slowCycledAction) {
            for (Action fastAction : fastCycledActions) {
                // make sure that all fast clicks executed on between all other long-running actions
                fastAction.perform();
            }
            slowAction.perform();
        }
    }

    @Override
    public void stopStrategy() {
        if (isOn) {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }
            isOn = false;
            Log.d(TAG, "Stopping");
        } else {
            Log.d(TAG, "Already stopped, skipping");
        }
    }

    @Override
    public void addOneTimeAction(Action action) {
        oneTimeActions.add(action);
    }
}
