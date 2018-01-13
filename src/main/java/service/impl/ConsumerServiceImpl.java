package service.impl;

import dao.DeviceStatusWithNearbyDao;
import model.DeviceStatusWithNearby;
import org.bson.types.ObjectId;
import service.ConsumerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ConsumerServiceImpl implements ConsumerService {

    @Inject
    DeviceStatusWithNearbyDao deviceStatusWithNearbyDao;

    public void addNewDeviceNearbyStatusRecord(DeviceStatusWithNearby record) {
        //TODO get mac vendor via macvendors api
        if(record != null) {
            deviceStatusWithNearbyDao.create(record);
        }
    }
}
