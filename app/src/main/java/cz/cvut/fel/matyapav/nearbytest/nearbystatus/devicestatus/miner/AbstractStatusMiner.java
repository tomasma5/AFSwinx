package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner;

import android.app.Activity;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public abstract class AbstractStatusMiner {

    private Activity activity;

    public abstract void mineAndFillStatus(DeviceStatus deviceStatus);

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
