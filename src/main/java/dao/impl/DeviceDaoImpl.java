package dao.impl;

import com.mongodb.client.MongoCollection;
import dao.DeviceDao;
import dao.database.MongoConnection;
import model.Device;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class DeviceDaoImpl implements DeviceDao {

    MongoCollection deviceCollection;

    public DeviceDaoImpl() {
        deviceCollection = MongoConnection.getInstance().getDatabase().getCollection(getCollectionName());
    }

    public String getCollectionName() {
        return "Devices";
    }

    public void createDevice(Device device) {
        deviceCollection.insertOne(device);
    }

    public void updateDevice(Device device) {
        Device toBeUpdated = findDeviceById(device.getId());
        deviceCollection.updateOne(toBeUpdated, device);
    }

    public Device findDeviceById(ObjectId id) {
        return (Device) deviceCollection.find(eq("id", id)).first();
    }

    public void deleteDeleteById(ObjectId id) {
        Device toBeRemoved = findDeviceById(id);
        deviceCollection.deleteOne(toBeRemoved);
    }

    public List findAll() {
        return (List) deviceCollection.find().into(new ArrayList());
    }


}
