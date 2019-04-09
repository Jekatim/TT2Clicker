package com.jekatim.tt2clicker.utils;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.jekatim.tt2clicker.R;

public class ColorChecker {

    private final Screenshooter screenshooter;
    private final Context context;
    private final int hueDelta = 10;

    public ColorChecker(Screenshooter screenshooter, Context context) {
        this.screenshooter = screenshooter;
        this.context = context;
    }

    public boolean isPresigeButton(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.prestige_button));
    }
    public boolean isPresigeConfirmButton(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.prestige_confirm_button));
    }

    public boolean isArtifactButton(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.artifact_button));
    }

    public boolean isClosePanelButton(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.close_panel_button));
    }

    public boolean isSlidePanelUpButton(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.slide_panel_up_button));
    }

    public boolean isUpgradeHeroButton(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.upgrade_hero_button))
                || isColorMatch(x, y, ContextCompat.getColor(context, R.color.upgrade_hero_button2));
    }

    public boolean isUnlockSkillButton(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.unlock_skill_button));
    }

    public boolean isCOSkillArea(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.CO_skill_area));
    }

    public boolean isNoCQAvailableArea(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.no_CQ_available_area));
    }

    public boolean isBossFailedArea(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.boss_fight_failed_area));
    }

    public boolean isNoUpgradeAvailable(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.no_upgrade_available));
    }

    public boolean isActiveSkillButton(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.active_skill_button));
    }

    public boolean isGoToSwordMasterTab(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.go_to_sword_master_tab));
    }

    public boolean isGoToHeroesTab(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.go_to_heroes_tab));
    }

    public boolean isGoToArtifactsTab(int x, int y) {
        return isColorMatch(x, y, ContextCompat.getColor(context, R.color.go_to_artifacts_tab));
    }

    private boolean isColorMatch(int x, int y, int desiredColor) {
        int color = screenshooter.getPixelColor(x, y);

        if (color == -1) {
            return false;
        }

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);

        float[] desiredHsv = new float[3];
        Color.colorToHSV(desiredColor, desiredHsv);

        if (hsv[2] > 0.2) {
            // not black
            if (desiredHsv[0] < hsv[0] + hueDelta
                    && desiredHsv[0] > hsv[0] - hueDelta) {
                // match within interval in 2*hueDelta
                return true;
            }
        }

        //either black or different color
        return false;
    }
}
