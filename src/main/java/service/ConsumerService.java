package service;

import model.DeviceStatusWithNearby;

/**
 * Service for storing data into application
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ConsumerService {

    /**
     * Adds new record of device with status and its nearby devices into database
     * @param record record to add
     */
    void addNewDeviceNearbyStatusRecord(DeviceStatusWithNearby record);

}
