package service;

import model.DeviceStatusWithNearby;
import model.partial.Device;
import rest.exception.NotFoundException;

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
     * @param timestamp        given timestamp
     * @return record with timestamp closest to given timestamp
     * @throws NotFoundException thrown if record was not found in database
     */
    DeviceStatusWithNearby findClosestToGivenTimestamp(String deviceIdentifier, long timestamp) throws NotFoundException;

    /**
     * Gets record from db which has same device identifier and same action and is closest to given timestamp
     *
     * @param deviceIdentifier given device identifier - most likely a mac address.
     * @param action           given action
     * @param timestamp        given timestamp
     * @return found record
     * @throws NotFoundException thrown if record was not found in database
     */
    DeviceStatusWithNearby findClosestToGivenTimestampWithAction(String deviceIdentifier, String action, long timestamp) throws NotFoundException;

    /**
     * Gets all records of devices with status and its nearby devices
     *
     * @param deviceIdentifier given device identifier - most likely a mac address. If null it would search over all devices
     * @return list of all records
     */
    List<DeviceStatusWithNearby> findAllDeviceStatusesWithNearbyDevices(String deviceIdentifier);


    /**
     * Gets last record with given device identifier and action
     *
     * @param deviceIdentifier given device identifier - most likely a mac address.
     * @param action           given action
     * @return specified record
     * @throws NotFoundException thrown if no record was found
     */
    DeviceStatusWithNearby findLastRecordWithAction(String deviceIdentifier, String action) throws NotFoundException;

}
