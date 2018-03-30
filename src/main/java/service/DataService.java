package service;

import model.DeviceStatusWithNearby;

import java.util.List;

/**
 * Service for getting data from application
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface DataService {

    /**
     * Gets closest record to given timestamp
     *
     * @param deviceIdentifier given device identifier - most likely a mac address. If null it would search over all devices
     * @param timestamp given timestamp
     * @return record with timestamp closest to given timestamp
     */
    DeviceStatusWithNearby findClosestToGivenTimestamp(String deviceIdentifier, long timestamp);

    /**
     * Gets all records of devices with status and its nearby devices
     * @param deviceIdentifier given device identifier - most likely a mac address. If null it would search over all devices
     * @return list of all records
     */
    List<DeviceStatusWithNearby> findAllDeviceStatusesWithNearbyDevices(String deviceIdentifier);

}
