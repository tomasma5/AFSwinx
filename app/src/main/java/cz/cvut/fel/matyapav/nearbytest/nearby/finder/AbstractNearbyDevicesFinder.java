package cz.cvut.fel.matyapav.nearbytest.nearby.finder;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.util.AppConstants;
import cz.cvut.fel.matyapav.nearbytest.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearby.model.DeviceAdditionalInfo;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public abstract class AbstractNearbyDevicesFinder {

    private List<Device> foundDevices = new ArrayList<>();

    public abstract void startFindingDevices();

    public abstract List<Device> stopFindingAndCollectDevices();

    List<Device> getFoundDevices() {
        return foundDevices;
    }

    void deviceFound(Device device) {
        if(!foundDevices.contains(device)){
            foundDevices.add(device);
            logDevice(device);
        }
    }

    private void logDevice(Device device) {
        StringBuilder logBuilder =  new StringBuilder();
        logBuilder.append("Device found! - ")
                .append(device.getName())
                .append("[ MAC ")
                .append(device.getMacAddress())
                .append("] TYPE = ").append(device.getDeviceType());
        if(device.getAdditionalInformations() != null) {
            logBuilder.append("\n Additional informations: \n");
            for (DeviceAdditionalInfo info : device.getAdditionalInformations()) {
                logBuilder.append(info.toString()).append("\n");
            }
        }
       Log.i(AppConstants.APPLICATION_TAG, logBuilder.toString());
    }
}
