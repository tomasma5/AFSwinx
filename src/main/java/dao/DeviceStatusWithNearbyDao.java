package dao;

import model.DeviceStatusWithNearby;

public abstract class DeviceStatusWithNearbyDao extends GenericMongoDao<DeviceStatusWithNearby>{

    protected Class getModelClass() {
        return DeviceStatusWithNearby.class;
    }

    protected String getCollectionName() {
        return "Devices";
    }
}
