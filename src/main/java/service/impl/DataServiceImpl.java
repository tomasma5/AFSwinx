package service.impl;

import dao.DeviceStatusWithNearbyDao;
import model.DeviceStatusWithNearby;
import service.DataService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

import static java.lang.Math.abs;

@ApplicationScoped
public class DataServiceImpl implements DataService {

    @Inject
    DeviceStatusWithNearbyDao deviceStatusWithNearbyDao;

    public List<DeviceStatusWithNearby> findAllDeviceStatusesWithNearbyDevices() {
        return deviceStatusWithNearbyDao.findAll();
    }

    @Override
    public DeviceStatusWithNearby findClosestToGivenTimestamp(long timestamp) {
        DeviceStatusWithNearby firstBigger = deviceStatusWithNearbyDao.getFirstLaterThanTimestamp(timestamp);
        DeviceStatusWithNearby firstLower = deviceStatusWithNearbyDao.getFirstEarlierThanTimestamp(timestamp);
        if(firstBigger != null && firstLower != null) {
            if (abs(timestamp - firstBigger.getTimestamp()) < abs(timestamp - firstLower.getTimestamp())) {
                return firstBigger;
            } else {
                return firstLower;
            }
        }
        return null;
    }
}
