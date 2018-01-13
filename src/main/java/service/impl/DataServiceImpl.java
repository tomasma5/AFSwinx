package service.impl;

import dao.DeviceStatusWithNearbyDao;
import model.DeviceStatusWithNearby;
import service.DataService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class DataServiceImpl implements DataService {

    @Inject
    DeviceStatusWithNearbyDao deviceStatusWithNearbyDao;

    public List<DeviceStatusWithNearby> findAllDeviceStatusesWithNearbyDevices() {
        return deviceStatusWithNearbyDao.findAll();
    }
}
