package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.task.FindDevicesTask;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.task.NearbyFinderEvent;

/**
 * Manages nearby device finding process
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class NearbyFinderManager {

    private Context context;
    private List<Device> devices;
    private List<AbstractNearbyDevicesFinder> nearbyDevicesFinders;
    private int recommendedTimeout = 10000; //10 seconds
    private FindDevicesTask findDevicesTask;

    NearbyFinderManager(Context context) {
        this.context = context;
        devices = new ArrayList<>();
    }

    /**
     * Runs the nearby device finding process
     *
     * @param callbackClass object which implements {@link NearbyFinderEvent} class
     *                      - onNearbyDevicesSearchFinished() method will be called at the end
     */
    void findNearbyDevices(NearbyFinderEvent callbackClass) {
        devices.clear();
        findDevicesTask = new FindDevicesTask(this, callbackClass).setRecommendedTimeout(recommendedTimeout);
        findDevicesTask.execute();
    }

    /**
     * Adds devices into found devices
     *
     * @param devices
     */
    public void addDevices(List<Device> devices) {
        for(Device device : devices){
            if(!this.devices.contains(device)){
                this.devices.add(device);
            }
        }
    }

    /**
     * Sets recommended timout of nearby devices finding process
     *
     * @param recommendedTimeout
     */
    void setRecommendedTimeout(int recommendedTimeout) {
        this.recommendedTimeout = recommendedTimeout;
    }

    /**
     * Adds nearby devices finder - if it is added it will be considered during nearby devices
     * finding process. It is also possible to specify battery level limit for specific finder. It
     * means the finder will not be used (turned off) if battery level is below this limit.
     *
     * @param nearbyDevicesFinder nearby devices finder
     * @param batteryLevelLimit   battery level limitation
     */
    void addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder, int batteryLevelLimit) {
        nearbyDevicesFinder.setBatteryLimit(batteryLevelLimit);
        addNearbyDevicesFinder(nearbyDevicesFinder);
    }

    /**
     * Adds nearby devices finder - if it is added it will be considered during nearby devices
     * finding process.
     *
     * @param nearbyDevicesFinder nearby devices finder
     */
    void addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder) {
        nearbyDevicesFinder.setContext(context);
        if (nearbyDevicesFinders == null) {
            nearbyDevicesFinders = new ArrayList<>();
        }
        nearbyDevicesFinders.add(nearbyDevicesFinder);
    }

    /**
     * Filters nearby devices finders by device status - e.g. it filters out those finders who does
     * not meet battery level limitations
     *
     * @param deviceStatus
     */
    void filterNearbyFindersByDeviceStatus(DeviceStatus deviceStatus) {
        List<AbstractNearbyDevicesFinder> abstractNearbyDevicesFinders = new ArrayList<>();
        for(AbstractNearbyDevicesFinder devicesFinder : nearbyDevicesFinders){
            if(deviceStatus.getBatteryStatus().getBatteryLevel() >= devicesFinder.getBatteryLimit()){
                abstractNearbyDevicesFinders.add(devicesFinder);
            }
        }
        nearbyDevicesFinders = abstractNearbyDevicesFinders;
    }

    public List<AbstractNearbyDevicesFinder> getNearbyDevicesFinders() {
        return nearbyDevicesFinders;
    }

    List<Device> getFoundDevices() {
        return devices;
    }

}
