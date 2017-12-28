package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.BatteryStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.DeviceInfo;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.LocationStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.NetworkStatus;

/**
 * Device status model - keeps all partial information about device in one place
 * Consisted of {@link DeviceInfo}, {@link BatteryStatus}, {@link LocationStatus}, {@link NetworkStatus}
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class DeviceStatus {

    private DeviceInfo deviceInfo;
    private BatteryStatus batteryStatus;
    private LocationStatus locationStatus;
    private NetworkStatus networkStatus;

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

    public NetworkStatus getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(NetworkStatus networkStatus) {
        this.networkStatus = networkStatus;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
