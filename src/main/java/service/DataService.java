package service;

import model.DeviceStatusWithNearby;

import java.util.List;

public interface DataService {

    List<DeviceStatusWithNearby> findAllDeviceStatusesWithNearbyDevices();

}
