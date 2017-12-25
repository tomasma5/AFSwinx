package cz.cvut.fel.matyapav.nearbytest.nearby;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.util.AppConstants;
import cz.cvut.fel.matyapav.nearbytest.ui.adapter.NearbyDeviceListAdapter;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.BTDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.FindDevicesTask;
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
    private ArrayAdapter<Device> adapter;


    public NearbyFinderManager(Activity activity) {
        this.activity = activity;
        devices = new ArrayList<>();
        adapter = new NearbyDeviceListAdapter(activity, devices);
    }

    public void findNearbyDevices() {
        devices.clear();
        adapter.notifyDataSetChanged();
        new FindDevicesTask(activity, this)
                .setRecommendedTimeout(10000)
                .addNearbyDevicesFinder(new BTDevicesFinder(activity))
                .addNearbyDevicesFinder(new SubnetDevicesFinder(activity))
                .addNearbyDevicesFinder(new NearbyNetworksFinder(activity))
                .execute();
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

    public List<Device> getFoundDevices() {
        return devices;
    }

    public ArrayAdapter<Device> getAdapter() {
        return this.adapter;
    }

}
