package com.jekatim.tt2clicker.service;

import java.io.Serializable;

public class SettingsModel implements Serializable {

    private boolean CQMode = false;
    private int cqTapPeriod = 50;

    public boolean isCQMode() {
        return CQMode;
    }

    public void setCQMode(boolean CQMode) {
        this.CQMode = CQMode;
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
                "CQMode=" + CQMode +
                ", cqTapPeriod=" + cqTapPeriod +
                '}';
    }
}
