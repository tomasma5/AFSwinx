package cz.cvut.fel.matyapav.nearbytest.devicestatus.model;

import android.app.Activity;

import cz.cvut.fel.matyapav.nearbytest.devicestatus.miner.BatteryStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.devicestatus.miner.LocationStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.devicestatus.model.partial.BatteryStatus;
import cz.cvut.fel.matyapav.nearbytest.devicestatus.model.partial.LocationStatus;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class DeviceStatus {

    private BatteryStatus batteryStatus;
    private LocationStatus locationStatus;

    public DeviceStatus() {
    }

    public BatteryStatus getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(BatteryStatus batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public LocationStatus getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(LocationStatus locationStatus) {
        this.locationStatus = locationStatus;
    }
}
