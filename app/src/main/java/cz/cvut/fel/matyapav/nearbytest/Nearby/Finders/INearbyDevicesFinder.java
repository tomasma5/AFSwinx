package cz.cvut.fel.matyapav.nearbytest.Nearby.Finders;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.Nearby.Device;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public abstract class INearbyDevicesFinder {

    private List<Device> foundDevices = new ArrayList<>();

    public abstract void startFindingDevices();

    public abstract List<Device> stopFindingAndCollectDevices();

    List<Device> getFoundDevices() {
        return foundDevices;
    }

    void deviceFound(Device device) {
        foundDevices.add(device);
    }
}
