package dao.impl;

import dao.DeviceStatusWithNearbyDao;
import dao.GenericMongoDao;
import model.DeviceStatusWithNearby;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@ApplicationScoped
public class DeviceStatusWithNearbyDaoImpl extends DeviceStatusWithNearbyDao {

    protected Class getModelClass() {
        return DeviceStatusWithNearby.class;
    }

    protected String getCollectionName() {
        return "Devices";
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
