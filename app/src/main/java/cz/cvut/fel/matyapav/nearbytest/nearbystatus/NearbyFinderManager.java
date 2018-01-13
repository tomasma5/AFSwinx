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
 * Manages nearby device finding process
 *
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

    /**
     * Runs the nearby device finding process
     * @param callbackClass object which implements {@link NearbyFinderVisitor} class
     *                      - onNearbyDevicesSearchFinished() method will be called at the end
     */
    void findNearbyDevices(NearbyFinderVisitor callbackClass) {
        devices.clear();
        if(findDevicesTask == null){
            findDevicesTask = new FindDevicesTask(activity, this, callbackClass).setRecommendedTimeout(recommendedTimeout);
        }
        findDevicesTask.execute();
    }

    /**
     * Adds devices into found devices
     * @param devices
     */
    public void addDevices(List<Device> devices) {
        devices.stream().filter(device -> !this.devices.contains(device)).forEach(device -> {
            this.devices.add(device);
        });
    }

    /**
     * Sets recommended timout of nearby devices finding process
     * @param recommendedTimeout
     */
    void setRecommendedTimeout(int recommendedTimeout){
        this.recommendedTimeout = recommendedTimeout;
    }

    /**
     * Adds nearby devices finder - if it is added it will be considered during nearby devices
     * finding process. It is also possible to specify battery level limit for specific finder. It
     * means the finder will not be used (turned off) if battery level is below this limit.
     *
     * @param nearbyDevicesFinder nearby devices finder
     * @param batteryLevelLimit battery level limitation
     */
    void addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder, int batteryLevelLimit){
        nearbyDevicesFinder.setBatteryLimit(batteryLevelLimit);
        addNearbyDevicesFinder(nearbyDevicesFinder);
    }

    /**
     * Adds nearby devices finder - if it is added it will be considered during nearby devices
     * finding process.
     * @param nearbyDevicesFinder nearby devices finder
     */
    void addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder){
        nearbyDevicesFinder.setActivity(activity);
        if (nearbyDevicesFinders == null) {
            nearbyDevicesFinders = new ArrayList<>();
        }
        nearbyDevicesFinders.add(nearbyDevicesFinder);
    }

    /**
     * Filters nearby devices finders by device status - e.g. it filters out those finders who does
     * not meet battery level limitations
     * @param deviceStatus
     */
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
