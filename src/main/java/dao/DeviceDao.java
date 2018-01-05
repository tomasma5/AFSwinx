package dao;

import model.Device;
import org.bson.types.ObjectId;

import java.util.List;

public interface DeviceDao {

    void createDevice(Device device);

    void updateDevice(Device device);

    Device findDeviceById(ObjectId id);

    void deleteDeleteById(ObjectId id);

    List<Device> findAll();

    String getCollectionName();

}
