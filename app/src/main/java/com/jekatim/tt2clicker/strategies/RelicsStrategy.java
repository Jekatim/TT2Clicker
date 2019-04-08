package com.jekatim.tt2clicker.strategies;

import android.util.Log;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.actions.ActivateCOAction;
import com.jekatim.tt2clicker.actions.ActivatePetHoMAction;
import com.jekatim.tt2clicker.actions.ActivateSCSkillAction;
import com.jekatim.tt2clicker.actions.CheckIfPushWithWCNeededAction;
import com.jekatim.tt2clicker.actions.CollectAstralAwakeningAction;
import com.jekatim.tt2clicker.actions.CollectFairiesAction;
import com.jekatim.tt2clicker.actions.FreeClicksAction;
import com.jekatim.tt2clicker.actions.PickUpEquipmentAction;
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
    private final ColorChecker colorChecker;

    private Timer timer;
    private boolean isOn = false;
    private final List<Action> cycledActions;
    private final Queue<Action> oneTimeActions;

    public RelicsStrategy(SettingsModel settings, ColorChecker colorChecker) {
        this.settings = settings;
        this.colorChecker = colorChecker;
        this.cycledActions = new ArrayList<>();
        this.oneTimeActions = new LinkedBlockingQueue<>();

        /*********************************************************/

//        addAfterPrestigeActions();

        /*********************************************************/

        cycledActions.add(new UpgradeHeroesAction(colorChecker));
        cycledActions.add(new UpgradeSwordMasterAction(colorChecker));
        cycledActions.add(new UpgradeSMNeededSkillsAction(colorChecker));
        cycledActions.add(new ActivateCOAction());
        cycledActions.add(new ActivatePetHoMAction());
        cycledActions.add(new ActivateSCSkillAction(colorChecker));
        cycledActions.add(new CollectAstralAwakeningAction(colorChecker));
        cycledActions.add(new CollectFairiesAction(colorChecker));
        cycledActions.add(new PickUpEquipmentAction(colorChecker));
        cycledActions.add(new FreeClicksAction());
        cycledActions.add(new CheckIfPushWithWCNeededAction(colorChecker, this));
    }

    @Override
    public void addAfterPrestigeActions() {
        oneTimeActions.add(new UpgradeSwordMasterAction(colorChecker));
        oneTimeActions.add(new UpgradeSMSkillsAction(colorChecker));
        oneTimeActions.add(new UpgradeHeroesAction(colorChecker));
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
            }, 1000, 1000);
            Log.d(TAG, "Launched");
            isOn = true;
        }
    }

    private void control() {
        while (oneTimeActions.peek() != null) {
            Action action = oneTimeActions.poll();
            action.perform();
        }
        for (Action action : cycledActions) {
            action.perform();
        }
    }

    @Override
    public void stopStrategy() {
        if (isOn) {
            if (timer != null) {
                timer.cancel();
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
