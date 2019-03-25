package com.jekatim.tt2clicker.actions;

import android.util.Log;

import com.jekatim.tt2clicker.service.AutoClickerService;
import com.jekatim.tt2clicker.settings.Coordinates;
import com.jekatim.tt2clicker.utils.ColorChecker;

public class PickUpEquipmentAction implements Action {

    private static String TAG = "PickUpEquipmentAction";

    private final ColorChecker colorChecker;
    private final Coordinates equipmentCoordinates = new Coordinates(800, 990);

    public PickUpEquipmentAction(ColorChecker colorChecker) {
        this.colorChecker = colorChecker;
    }

    @Override
    public void perform() {
        // close tab if needed
        CommonSteps.closePanel(colorChecker);
        // click several time on equipment area
        Log.d(TAG, "Clicking on equipment");
        AutoClickerService.instance.click(equipmentCoordinates.x, equipmentCoordinates.y);
        pause(100);
        AutoClickerService.instance.click(equipmentCoordinates.x, equipmentCoordinates.y);
        pause(100);
        AutoClickerService.instance.click(equipmentCoordinates.x, equipmentCoordinates.y);
        pause(100);
        // wait until splash for 2 sec
        pause(2000);
        // click several time to close popup
        AutoClickerService.instance.click(equipmentCoordinates.x, equipmentCoordinates.y);
        pause(100);
        AutoClickerService.instance.click(equipmentCoordinates.x, equipmentCoordinates.y);
        pause(100);
        AutoClickerService.instance.click(equipmentCoordinates.x, equipmentCoordinates.y);
        pause(100);
    }

    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
