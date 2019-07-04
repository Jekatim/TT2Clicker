package com.jekatim.tt2clicker.actions.csbuild;

import android.util.Log;

import com.jekatim.tt2clicker.actions.ActionWithPeriod;
import com.jekatim.tt2clicker.actions.CommonSteps;
import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

import static com.jekatim.tt2clicker.actions.CommonSteps.pause500;

public class ActivateSupportSkillsAction extends ActionWithPeriod {

    private static String TAG = "ActivateSupportSkillsAction";

    private final ColorChecker colorChecker;
    private static final Coordinates dsCoordinates = new Coordinates(270, 1720);
    private static final Coordinates hmCoordinates = new Coordinates(450, 1720);
    private static final Coordinates fsCoordinates = new Coordinates(620, 1720);
    private static final Coordinates scCoordinates = new Coordinates(1000, 1720);

    public ActivateSupportSkillsAction(ColorChecker colorChecker) {
        super(130); //sec
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        if (!checkTimePeriodValid()) {
            return;
        }
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // click on skills button
        Log.d(TAG, "Activating support skills");
        AutoClickerService.instance.click(dsCoordinates.x, dsCoordinates.y);
        pause500();
        AutoClickerService.instance.click(hmCoordinates.x, hmCoordinates.y);
        pause500();
        AutoClickerService.instance.click(fsCoordinates.x, fsCoordinates.y);
        pause500();
        AutoClickerService.instance.click(scCoordinates.x, scCoordinates.y);
        pause500();
        lastActivatedTime = System.currentTimeMillis();
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
