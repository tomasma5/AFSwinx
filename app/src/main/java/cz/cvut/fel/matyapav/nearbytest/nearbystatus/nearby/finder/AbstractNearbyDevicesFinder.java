package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;

/**
 * Abstract template for all nearby devices finders
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public abstract class AbstractNearbyDevicesFinder {

    private List<Device> foundDevices = new ArrayList<>();
    private Context context;
    private int batteryLimit;

    /**
     * Starts finding nearby devices
     */
    public abstract void startFindingDevices();

    /**
     * Stops finding nearby devices and collects them
     * @return found nearby devices
     */
    public abstract List<Device> stopFindingAndCollectDevices();

    /**
     * Adds found device into found devices list and logs it
     * @param device
     */
    public void deviceFound(Device device) {
        if(!foundDevices.contains(device)){
            foundDevices.add(device);
            logDevice(device);
        }
    }

    /**
     * Logs found device
     * @param device found device
     */
    private void logDevice(Device device) {
        StringBuilder logBuilder =  new StringBuilder();
        logBuilder.append("Device found! - ")
                .append(device.getName())
                .append("[ MAC ")
                .append(device.getMacAddress())
                .append("] TYPE = ").append(device.getDeviceType());
        if(device.getAdditionalInformations() != null) {
            logBuilder.append("\n Additional informations: \n");
            for (Map.Entry<String, String> info : device.getAdditionalInformations().entrySet()) {
                logBuilder.append(info.getKey()).append(" : ").append(info.getValue()).append("\n");
            }
        }
       Log.i(GlobalConstants.APPLICATION_TAG, logBuilder.toString());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getBatteryLimit() {
        return batteryLimit;
    }

    public void setBatteryLimit(int batteryLimit) {
        this.batteryLimit = batteryLimit;
    }

    protected List<Device> getFoundDevices() {
        return foundDevices;
    }

}
