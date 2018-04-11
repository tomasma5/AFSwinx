package service.impl;

import dao.DeviceStatusWithNearbyDao;
import model.DeviceStatusWithNearby;
import model.partial.Device;
import rest.exception.NotFoundException;
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
    public DeviceStatusWithNearby findClosestToGivenTimestamp(String deviceIdentifier, long timestamp) throws NotFoundException {
        DeviceStatusWithNearby firstBigger = deviceStatusWithNearbyDao.getFirstLaterThanTimestamp(deviceIdentifier, timestamp);
        DeviceStatusWithNearby firstLower = deviceStatusWithNearbyDao.getFirstEarlierThanTimestamp(deviceIdentifier, timestamp);
        DeviceStatusWithNearby closest = determineClosestToTimestamp(timestamp, firstBigger, firstLower);
        if (closest == null) {
            throw new NotFoundException("Record for specified time was not found. Time - " + timestamp);
        }
        return closest;
    }

    private DeviceStatusWithNearby determineClosestToTimestamp(long timestamp, DeviceStatusWithNearby firstBigger, DeviceStatusWithNearby firstLower) {
        if (firstBigger != null && firstLower != null) {
            if (abs(timestamp - firstBigger.getTimestamp()) < abs(timestamp - firstLower.getTimestamp())) {
                return firstBigger;
            } else {
                return firstLower;
            }
        } else if (firstBigger != null) {
            return firstBigger;
        } else if (firstLower != null) {
            return firstLower;
        }
        return null;
    }

    @Override
    public DeviceStatusWithNearby findClosestToGivenTimestampWithAction(String deviceIdentifier, String action, long timestamp) throws NotFoundException {
        DeviceStatusWithNearby firstBigger = deviceStatusWithNearbyDao.getFirstLaterThanTImestampWithGivenAction(deviceIdentifier, action, timestamp);
        DeviceStatusWithNearby firstLower = deviceStatusWithNearbyDao.getFirstEarlierThanTimestampWithGivenAction(deviceIdentifier, action, timestamp);
        DeviceStatusWithNearby closest = determineClosestToTimestamp(timestamp, firstBigger, firstLower);
        if (closest == null) {
            throw new NotFoundException("Record for specified time and action was not found. Action - " + action + ", Time - " + timestamp);
        }
        return closest;
    }

    @Override
    public List<DeviceStatusWithNearby> findAllDeviceStatusesWithNearbyDevices(String deviceIdentifier) {
        return deviceStatusWithNearbyDao.findAll(deviceIdentifier);
    }

    @Override
    public DeviceStatusWithNearby findLastRecordWithAction(String deviceIdentifier, String action) throws NotFoundException {
        DeviceStatusWithNearby lastRecord = deviceStatusWithNearbyDao.getFirstEarlierThanTimestampWithGivenAction(deviceIdentifier, action, System.currentTimeMillis());
        if (lastRecord == null) {
            throw new NotFoundException("There are no records with given action (= " + action + " ) and device identifier (= " + deviceIdentifier + " ).");
        }
        return lastRecord;
    }
}
