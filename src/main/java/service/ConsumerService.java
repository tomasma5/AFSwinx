package service;

import model.DeviceStatusWithNearby;

public interface ConsumerService {

    void addNewDeviceNearbyStatusRecord(DeviceStatusWithNearby record);

}
