package com.jekatim.tt2clicker.strategies;

import com.jekatim.tt2clicker.settings.ClickingStrategy;

public interface Strategy {

    ClickingStrategy getType();

    void launchStrategy();

    void stopStrategy();
}
