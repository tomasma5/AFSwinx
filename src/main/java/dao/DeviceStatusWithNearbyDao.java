package dao;

import model.DeviceStatusWithNearby;

/**
 * Mongo DAO for devices with status and its nearby devices
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public abstract class DeviceStatusWithNearbyDao extends GenericMongoDao<DeviceStatusWithNearby>{

    protected Class getModelClass() {
        return DeviceStatusWithNearby.class;
    }

    protected String getCollectionName() {
        return "Devices";
    }

    /**
     * Gets first record which timestamp is lower (earlier) than given timestamp
     * @param timestamp given timestamp
     * @return first record which timestamp is lower
     */
    public abstract DeviceStatusWithNearby getFirstEarlierThanTimestamp(long timestamp);

    /**
     * Gets first record which timestamp is bigger (later) than given timestamp
     * @param timestamp given timestamp
     * @return first record which timestamp is bigger (later)
     */
    public abstract DeviceStatusWithNearby getFirstLaterThanTimestamp(long timestamp);
}
