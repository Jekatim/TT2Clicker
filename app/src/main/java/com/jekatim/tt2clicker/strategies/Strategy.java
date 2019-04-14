package com.jekatim.tt2clicker.strategies;

import com.jekatim.tt2clicker.actions.Action;
import com.jekatim.tt2clicker.settings.ClickingStrategy;

public interface Strategy {

    ClickingStrategy getType();

    void launchStrategy();

    boolean isLaunched();

    void stopStrategy();

    void addOneTimeAction(Action action);

    void addAfterPrestigeActions();
}
