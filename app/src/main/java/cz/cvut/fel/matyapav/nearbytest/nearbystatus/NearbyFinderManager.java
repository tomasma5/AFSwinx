package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.SubnetDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.task.FindDevicesTask;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.task.NearbyFinderVisitor;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyFinderManager {

    private Activity activity;
    private List<Device> devices;
    private List<AbstractNearbyDevicesFinder> nearbyDevicesFinders;
    private int recommendedTimeout = 10000; //10 seconds
    private FindDevicesTask findDevicesTask;

    NearbyFinderManager(Activity activity) {
        this.activity = activity;
        devices = new ArrayList<>();
    }

    void findNearbyDevices(NearbyFinderVisitor callbackClass) {
        devices.clear();
        if(findDevicesTask == null){
            findDevicesTask = new FindDevicesTask(activity, this, callbackClass).setRecommendedTimeout(recommendedTimeout);
        }
        findDevicesTask.execute();
    }

    public void addDevices(List<Device> devices) {
        devices.stream().filter(device -> !this.devices.contains(device)).forEach(device -> {
            this.devices.add(device);
        });
    }

    void setRecommendedTimeout(int recommendedTimeout){
        this.recommendedTimeout = recommendedTimeout;
    }

    void addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder, int batteryLevelLimit){
        nearbyDevicesFinder.setBatteryLimit(batteryLevelLimit);
        addNearbyDevicesFinder(nearbyDevicesFinder);
    }

    void addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder){
        nearbyDevicesFinder.setActivity(activity);
        if (nearbyDevicesFinder instanceof SubnetDevicesFinder) {
            ((SubnetDevicesFinder) nearbyDevicesFinder).setTimeOutMillis(recommendedTimeout);
        }
        if (nearbyDevicesFinders == null) {
            nearbyDevicesFinders = new ArrayList<>();
        }
        nearbyDevicesFinders.add(nearbyDevicesFinder);
    }

    void filterNearbyFindersByDeviceStatus(DeviceStatus deviceStatus) {
        nearbyDevicesFinders = nearbyDevicesFinders.stream()
                .filter(finder -> deviceStatus.getBatteryStatus().getBatteryLevel() >= finder.getBatteryLimit())
                .collect(Collectors.toList());
    }

    public List<AbstractNearbyDevicesFinder> getNearbyDevicesFinders() {
        return nearbyDevicesFinders;
    }

    List<Device> getFoundDevices() {
        return devices;
    }

}
