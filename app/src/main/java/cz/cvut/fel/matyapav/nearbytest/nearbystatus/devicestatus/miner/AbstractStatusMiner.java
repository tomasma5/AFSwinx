package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner;

import android.app.Activity;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;

/**
 * Abstract template for all device status miners
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 *
 */
public abstract class AbstractStatusMiner {

    private Activity activity;

    /**
     * Gets desired status information from device and fills these information into {@link DeviceStatus} object
     * @param deviceStatus device status where the information should be filled in
     */
    public abstract void mineAndFillStatus(DeviceStatus deviceStatus);

    Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
