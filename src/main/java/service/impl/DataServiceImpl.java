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

    @Override
    public DeviceStatusWithNearby findClosestToGivenTimestamp(String deviceIdentifier, long timestamp) {
        DeviceStatusWithNearby firstBigger = deviceStatusWithNearbyDao.getFirstLaterThanTimestamp(deviceIdentifier, timestamp);
        DeviceStatusWithNearby firstLower = deviceStatusWithNearbyDao.getFirstEarlierThanTimestamp(deviceIdentifier, timestamp);
        if (firstBigger != null && firstLower != null) {
            if (abs(timestamp - firstBigger.getTimestamp()) < abs(timestamp - firstLower.getTimestamp())) {
                return firstBigger;
            } else {
                return firstLower;
            }
        } else if(firstBigger != null){
            return firstBigger;
        } else if(firstLower != null) {
            return firstLower;
        }
        return null;
    }

    @Override
    public List<DeviceStatusWithNearby> findAllDeviceStatusesWithNearbyDevices(String deviceIdentifier) {
        return deviceStatusWithNearbyDao.findAll(deviceIdentifier);
    }
}
