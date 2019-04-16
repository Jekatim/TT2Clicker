package com.jekatim.tt2clicker.strategies;

import android.content.Context;
import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.actions.CollectAllClicksAction;
import com.jekatim.tt2clicker.actions.CollectDailyRewardAction;
import com.jekatim.tt2clicker.actions.PickUpEquipmentAction;
import com.jekatim.tt2clicker.actions.ScrollUpAfterPrestigeAction;
import com.jekatim.tt2clicker.actions.UpgradeHeroesAction;
import com.jekatim.tt2clicker.actions.UpgradeSwordMasterAction;
import com.jekatim.tt2clicker.actions.csbuild.ActivateSCSkillAction;
import com.jekatim.tt2clicker.actions.csbuild.CheckIfPushWithWCNeededAction;
import com.jekatim.tt2clicker.actions.csbuild.UpgradeSMNeededSkillsAction;
import com.jekatim.tt2clicker.actions.csbuild.UpgradeSMSkillsAction;
import com.jekatim.tt2clicker.settings.ClickingStrategy;
import com.jekatim.tt2clicker.settings.SettingsModel;
import com.jekatim.tt2clicker.utils.ColorChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class RelicsStrategy extends AbstractStrategy {

    private static String TAG = "RelicsStrategy";

    private final SettingsModel settings;
    private final ColorChecker colorChecker;
    private final List<Action> slowCycledAction;
    private final List<Action> fastCycledActions;
    private final Queue<Action> oneTimeActions;

    public RelicsStrategy(SettingsModel settings, ColorChecker colorChecker, Context context) {
        super(context);
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
        oneTimeActions.add(new CollectDailyRewardAction(colorChecker));

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
        for (Action slowAction : slowCycledAction) {
            for (Action fastAction : fastCycledActions) {
                if (!isLaunched) {
                    return;
                }
                // make sure that all fast clicks executed on between all other long-running actions
                fastAction.perform();
            }
            slowAction.perform();
        }
    }

    @Override
    public void addOneTimeAction(Action action) {
        oneTimeActions.add(action);
    }
}
