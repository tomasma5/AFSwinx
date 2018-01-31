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
     * Gets all records of devices with status and its nearby devices
     * @return list of all records
     */
    List<DeviceStatusWithNearby> findAllDeviceStatusesWithNearbyDevices();

    /**
     * Gets closest record to given timestamp
     * @param timestamp given timestamp
     * @return record with timestamp closest to given timestamp
     */
    DeviceStatusWithNearby findClosestToGivenTimestamp(long timestamp);

}
