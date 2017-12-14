package cz.cvut.fel.matyapav.nearbytest.Nearby;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.Helpers.AppConstants;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Adapters.NearbyDeviceListAdapter;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Finders.BTDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Finders.NearbyNetworksFinder;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Finders.SubnetDevicesFinder;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyDevicesFinder {

    private Activity activity;
    private List<Device> devices;
    private ArrayAdapter<Device> adapter;


    public NearbyDevicesFinder(Activity activity) {
        this.activity = activity;
        devices = new ArrayList<>();
        adapter = new NearbyDeviceListAdapter(activity, devices);
    }

    public void findNearbyDevices() {
        devices.clear();
        //TODO make all of them run in background ...
        //access points
        new NearbyNetworksFinder(activity, this).findDevices();
        //subnet devices
        new SubnetDevicesFinder(activity, this).findDevices();
        //bluetooth
        new BTDevicesFinder(activity, this).findDevices();
    }

    public void addDevices(List<Device> devices) {
        boolean somethingNew = false;
        for (Device device: devices) {
            if(!this.devices.contains(device)){
                this.devices.add(device);
                somethingNew = true;
            }
        }
        if(somethingNew) {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            } else {
                Log.e(AppConstants.APPLICATION_TAG, "Cannot update adapter collection - maybe you forgot to set it?");
            }
        }
    }

    public void addDevice(Device device){
        if(!devices.contains(device)) {
            devices.add(device);
            if(adapter != null) {
                adapter.notifyDataSetChanged();
            } else {
                Log.e(AppConstants.APPLICATION_TAG, "Cannot update adapter collection - maybe you forgot to set it?");
            }
        }
    }

    public List<Device> getFoundDevices() {
        return devices;
    }

    public ArrayAdapter<Device> getAdapter() {
        return this.adapter;
    }
}
