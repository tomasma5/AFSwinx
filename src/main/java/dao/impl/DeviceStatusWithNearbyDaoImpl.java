package dao.impl;

import dao.DeviceStatusWithNearbyDao;
import dao.GenericMongoDao;
import model.DeviceStatusWithNearby;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static java.lang.Math.abs;

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

    @Override
    public DeviceStatusWithNearby getFirstEarlierThanTimestamp(long timestamp) {
        return collection.find(lt("timestamp", timestamp))
                .sort(descending("timestamp"))
                .limit(1)
                .first();
    }

    @Override
    public DeviceStatusWithNearby getFirstLaterThanTimestamp(long timestamp) {
        return collection.find(gt("timestamp", timestamp))
                .sort(ascending("timestamp"))
                .limit(1)
                .first();
    }

    public void create(DeviceStatusWithNearby record) {
        collection.insertOne(record);
    }

    public void update(DeviceStatusWithNearby record) {
        collection.replaceOne(eq("id", record.getId()), record);
    }

    public DeviceStatusWithNearby findByObjectId(ObjectId id) {
        return collection.find(eq("id", id)).first();
    }

    public void deleteByObjectId(ObjectId id) {
        collection.deleteOne(eq("id", id));
    }

    public List<DeviceStatusWithNearby> findAll() {
        return collection.find().into(new ArrayList<DeviceStatusWithNearby>());
    }
}
