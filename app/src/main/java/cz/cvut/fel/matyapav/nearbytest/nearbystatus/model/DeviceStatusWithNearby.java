package cz.cvut.fel.matyapav.nearbytest.nearbystatus.model;

import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;

/**
 * Groups device status and nearby device together into single model with timestamp
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class DeviceStatusWithNearby {

    private long timestamp;
    private DeviceStatus deviceStatus;
    private List<Device> nearbyDevices;

    public DeviceStatusWithNearby() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public List<Device> getNearbyDevices() {
        return nearbyDevices;
    }

    public void setNearbyDevices(List<Device> nearbyDevices) {
        this.nearbyDevices = nearbyDevices;
    }
}
