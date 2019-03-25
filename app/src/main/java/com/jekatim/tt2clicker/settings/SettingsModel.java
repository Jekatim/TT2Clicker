package com.jekatim.tt2clicker.settings;

import java.io.Serializable;

public class SettingsModel implements Serializable {

    private ClickingStrategy strategy;
    private int cqTapPeriod = 500;

    public ClickingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ClickingStrategy strategy) {
        this.strategy = strategy;
    }

    public int getCqTapPeriod() {
        return cqTapPeriod;
    }

    public void setCqTapPeriod(int cqTapPeriod) {
        this.cqTapPeriod = cqTapPeriod;
    }

    @Override
    public String toString() {
        return "SettingsModel{" +
                "strategy=" + strategy +
                ", cqTapPeriod=" + cqTapPeriod +
                '}';
    }
}
