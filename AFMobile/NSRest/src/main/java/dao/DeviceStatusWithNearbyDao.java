package dao;

import model.DeviceStatusWithNearby;

import java.util.List;

/**
 * Mongo DAO for devices with status and its nearby devices
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public abstract class DeviceStatusWithNearbyDao extends GenericMongoDao<DeviceStatusWithNearby> {

    protected Class getModelClass() {
        return DeviceStatusWithNearby.class;
    }

    protected String getCollectionName() {
        return "Devices";
    }

    /**
     * Gets first record which timestamp is lower (earlier) than given timestamp
     *
     * @param deviceIdentifier given device identifier - most likely a mac address of device. If null it will search through all devices
     * @param timestamp        given timestamp
     * @return first record which timestamp is lower
     */
    public abstract DeviceStatusWithNearby getFirstEarlierThanTimestamp(String deviceIdentifier, long timestamp);

    /**
     * Gets first record which timestamp is bigger (later) than given timestamp
     *
     * @param deviceIdentifier given device identifier - most likely a mac address of device. If null it will search through all devices
     * @param timestamp        given timestamp
     * @return first record which timestamp is bigger (later)
     */
    public abstract DeviceStatusWithNearby getFirstLaterThanTimestamp(String deviceIdentifier, long timestamp);

    /**
     * Gets all @{@link DeviceStatusWithNearby} records
     *
     * @param deviceIdentifier given device identifier - most likely a mac address. If null it will search through all devices
     * @return list of @{@link DeviceStatusWithNearby} recors for this device
     */
    public abstract List<DeviceStatusWithNearby> findAll(String deviceIdentifier);

    /**
     * Gets first record which timestamp is bigger (later) than given timestamp with given action and device identifier
     *
     * @param deviceIdentifier given device identifier - most likely a mac address of device.
     * @param action           given action
     * @param timestamp        given timestamp
     * @return first later record which has given action
     */
    public abstract DeviceStatusWithNearby getFirstLaterThanTImestampWithGivenAction(String deviceIdentifier, String action, long timestamp);

    /**
     * Gets first record which timestamp is smaller (earlier) than given timestamp with given action and device identifier
     *
     * @param deviceIdentifier given device identifier - most likely a mac address of device.
     * @param action           given action
     * @param timestamp        given timestamp
     * @return first earlier record which has given action
     */
    public abstract DeviceStatusWithNearby getFirstEarlierThanTimestampWithGivenAction(String deviceIdentifier, String action, long timestamp);
}
