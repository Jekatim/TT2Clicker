package com.jekatim.tt2clicker.settings;

import java.io.Serializable;

public class SettingsModel implements Serializable {

    private ClickingStrategy strategy = ClickingStrategy.RELIC_MODE;
    private int autoPrestigeAfter = 30;

    public ClickingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ClickingStrategy strategy) {
        this.strategy = strategy;
    }

    public int getAutoPrestigeAfter() {
        return autoPrestigeAfter;
    }

    public void setAutoPrestigeAfter(int autoPrestigeAfter) {
        this.autoPrestigeAfter = autoPrestigeAfter;
    }

    @Override
    public String toString() {
        return "SettingsModel{" +
                "strategy=" + strategy +
                ", autoPrestigeAfter=" + autoPrestigeAfter +
                '}';
    }
}
