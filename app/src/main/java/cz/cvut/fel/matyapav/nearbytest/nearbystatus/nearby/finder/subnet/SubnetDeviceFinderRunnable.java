package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.subnet;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.NearbyConstants;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.NetworkUtils;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;

/**
 *  Runnable which checks if given ip address is reachable
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
class SubnetDeviceFinderRunnable implements Runnable {

    private final String address;
    private AbstractNearbyDevicesFinder finder;

    SubnetDeviceFinderRunnable(String address, AbstractNearbyDevicesFinder finder) {
        this.address = address;
        this.finder = finder;
    }

    @Override
    public void run() {
        try {
            InetAddress ia = InetAddress.getByName(address);
            boolean reachable = ia.isReachable(100);
            if (reachable) {
                String macAddress = NetworkUtils.getMacAddressFromIp(ia.getHostAddress());
                if (!macAddress.equals(NearbyConstants.EMPTY_MAC_ADDRESS)) { //add only devices with mac address readable from ARP table
                    Log.w(GlobalConstants.APPLICATION_TAG, ia.getCanonicalHostName() + " " + macAddress + " " + DeviceType.WIFI_DEVICE);
                    subnetDeviceFound(new Device(ia.getCanonicalHostName(), macAddress, DeviceType.WIFI_DEVICE));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when subnet device is found
     *
     * @param device found device
     */
    private synchronized void subnetDeviceFound(Device device) {
        finder.deviceFound(device);
    }
}
