package dao.impl;

import com.mongodb.client.FindIterable;
import dao.DeviceStatusWithNearbyDao;
import model.DeviceStatusWithNearby;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

/**
 * Implementation of Mongo DAO for devices with status and its nearby devices
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class DeviceStatusWithNearbyDaoImpl extends DeviceStatusWithNearbyDao {

    protected Class getModelClass() {
        return DeviceStatusWithNearby.class;
    }

    protected String getCollectionName() {
        return "Devices";
    }

    private static final String ID_FIELD = "id";
    private static final String MAC_ADDRESS_FIELD = "deviceStatus.deviceInfo.macAddress";
    private static final String TIMESTAMP_FIELD = "timestamp";
    private static final String ACTION_FIELD = "deviceStatus.applicationState.action";

    @Override
    public List<DeviceStatusWithNearby> findAll(String deviceIdentifier) {
        if (deviceIdentifier == null) {
            return collection.find().into(new ArrayList<DeviceStatusWithNearby>());
        } else {
            return collection.find(eq(MAC_ADDRESS_FIELD, deviceIdentifier)).into(new ArrayList<DeviceStatusWithNearby>());
        }
    }

    @Override
    public DeviceStatusWithNearby getFirstEarlierThanTimestamp(String deviceIdentifier, long timestamp) {
        FindIterable<DeviceStatusWithNearby> finder;
        if (deviceIdentifier == null) {
            finder = collection.find(lt(TIMESTAMP_FIELD, timestamp));
        } else {
            finder = collection.find(and(
                    eq(MAC_ADDRESS_FIELD, deviceIdentifier),
                    lt(TIMESTAMP_FIELD, timestamp)
            ));
        }
        return finder.sort(descending(TIMESTAMP_FIELD))
                .limit(1)
                .first();
    }

    @Override
    public DeviceStatusWithNearby getFirstLaterThanTimestamp(String deviceIdentifier, long timestamp) {
        FindIterable<DeviceStatusWithNearby> finder;
        if (deviceIdentifier == null) {
            finder = collection.find(gt(TIMESTAMP_FIELD, timestamp));
        } else {
            finder = collection.find(and(
                    eq(MAC_ADDRESS_FIELD, deviceIdentifier),
                    gt(TIMESTAMP_FIELD, timestamp)
            ));
        }
        return finder.sort(ascending(TIMESTAMP_FIELD))
                .limit(1)
                .first();
    }

    public DeviceStatusWithNearby getFirstEarlierThanTimestampWithGivenAction(String deviceIdentifier, String action, long timestamp) {
        return collection.find(and(
                eq(MAC_ADDRESS_FIELD, deviceIdentifier),
                lt(TIMESTAMP_FIELD, timestamp),
                eq(ACTION_FIELD, action)
        ))
                .sort(descending(TIMESTAMP_FIELD))
                .limit(1)
                .first();
    }

    public DeviceStatusWithNearby getFirstLaterThanTImestampWithGivenAction(String deviceIdentifier, String action, long timestamp) {
        return collection.find(and(
                eq(MAC_ADDRESS_FIELD, deviceIdentifier),
                gt(TIMESTAMP_FIELD, timestamp),
                eq(action)
        ))
                .sort(ascending(TIMESTAMP_FIELD))
                .limit(1)
                .first();
    }

    public void create(DeviceStatusWithNearby record) {
        collection.insertOne(record);
    }

    public void update(DeviceStatusWithNearby record) {
        collection.replaceOne(eq(ID_FIELD, record.getId()), record);
    }

    public DeviceStatusWithNearby findByObjectId(ObjectId id) {
        return collection.find(eq(ID_FIELD, id)).first();
    }

    public void deleteByObjectId(ObjectId id) {
        collection.deleteOne(eq(ID_FIELD, id));
    }

    public List<DeviceStatusWithNearby> findAll() {
        return collection.find().into(new ArrayList<DeviceStatusWithNearby>());
    }
}
