package cz.cvut.fel.matyapav.nearbytest.devicestatus.miner;

import android.app.Activity;

import cz.cvut.fel.matyapav.nearbytest.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.devicestatus.model.partial.LocationStatus;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class LocationStatusMiner extends AbstractStatusMiner {

    public LocationStatusMiner(Activity activity) {
        super(activity);
    }

    @Override
    public void mineAndFillStatus(DeviceStatus deviceStatus) {
        LocationStatus locationStatus = new LocationStatus();
        //TODO
        deviceStatus.setLocationStatus(locationStatus);
    }
}
