package com.jekatim.tt2clicker.settings;

import java.io.Serializable;

public class SettingsModel implements Serializable {

    private ClickingStrategy strategy = ClickingStrategy.CQ_MODE;
    private int cqTapPeriod = 50;
    private int heroesScrollStartIndex = 5;

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

    public int getHeroesScrollStartIndex() {
        return heroesScrollStartIndex;
    }

    public void setHeroesScrollStartIndex(int heroesScrollStartIndex) {
        this.heroesScrollStartIndex = heroesScrollStartIndex;
    }

    @Override
    public String toString() {
        return "SettingsModel{" +
                "strategy=" + strategy +
                ", cqTapPeriod=" + cqTapPeriod +
                ", heroesScrollStartIndex=" + heroesScrollStartIndex +
                '}';
    }
}
