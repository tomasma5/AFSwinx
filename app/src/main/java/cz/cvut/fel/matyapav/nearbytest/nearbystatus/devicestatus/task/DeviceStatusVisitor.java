package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.task;

/**
 * Callback interface for device status mining
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public interface DeviceStatusVisitor {

    /**
     * Should be called when device status is mined and filled
     */
    void onDeviceStatusMined();

}
