package cz.cvut.fel.matyapav.nearbytest.nearby;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.BTBondedDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.util.AppConstants;
import cz.cvut.fel.matyapav.nearbytest.ui.adapter.NearbyDeviceListAdapter;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.BTDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearby.task.FindDevicesTask;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.NearbyNetworksFinder;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.SubnetDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearby.model.Device;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyFinderManager {

    private Activity activity;
    private List<Device> devices;
    private DeviceStatus deviceStatus; //TODO
    private ArrayAdapter<Device> adapter;
    private FindDevicesTask findDevicesTask;
    private int recommendedTimeout = 10000; //10 seconds

    public NearbyFinderManager(Activity activity) {
        this.activity = activity;
        devices = new ArrayList<>();
        adapter = new NearbyDeviceListAdapter(activity, devices);
        findDevicesTask =  new FindDevicesTask(activity, this).setRecommendedTimeout(recommendedTimeout);
    }

    public void findNearbyDevices() {
        devices.clear();
        adapter.notifyDataSetChanged();
        findDevicesTask.execute();
    }

    public void addDevices(List<Device> devices) {
        boolean somethingNew = false;
        for (Device device : devices) {
            if (!this.devices.contains(device)) {
                this.devices.add(device);
                somethingNew = true;
            }
        }
        if (somethingNew) {
            if (adapter != null) {
                activity.runOnUiThread(() -> adapter.notifyDataSetChanged());
            } else {
                Log.e(AppConstants.APPLICATION_TAG, "Cannot update adapter collection - maybe you forgot to set it?");
            }
        }
    }

    public NearbyFinderManager setRecommendedTimeout(int recommendedTimeout){
        this.recommendedTimeout = recommendedTimeout;
        return this;
    }

    public NearbyFinderManager addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder, int batteryLevelLimit){
        if(deviceStatus == null){
            Log.e(AppConstants.APPLICATION_TAG, "Device status was not set (maybe you forgot to set it?), adding nearby device finder without battery level limitation...");
            return addNearbyDevicesFinder(nearbyDevicesFinder);
        }
        //TODO consider this ...
        if(deviceStatus.getBatteryStatus().getBatteryLevel() >= batteryLevelLimit){
            findDevicesTask.addNearbyDevicesFinder(nearbyDevicesFinder);
        }
        return this;
    }

    public NearbyFinderManager addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder){
        findDevicesTask.addNearbyDevicesFinder(nearbyDevicesFinder);
        return this;
    }

    public NearbyFinderManager setDeviceStatus(DeviceStatus status){
        this.deviceStatus = status;
        return this;
    }

    public List<Device> getFoundDevices() {
        return devices;
    }

    public ArrayAdapter<Device> getAdapter() {
        return this.adapter;
    }

}
