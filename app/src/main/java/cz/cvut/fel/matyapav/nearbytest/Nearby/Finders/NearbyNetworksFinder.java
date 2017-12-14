package cz.cvut.fel.matyapav.nearbytest.Nearby.Finders;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.Nearby.Device;
import cz.cvut.fel.matyapav.nearbytest.Nearby.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.Nearby.NearbyDevicesFinder;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyNetworksFinder implements INearbyDevicesFinder {

    private WifiManager wifiManager;
    private NearbyDevicesFinder finder;

    public NearbyNetworksFinder(Activity activity, NearbyDevicesFinder finder) {
        wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.finder = finder;
    }

    @Override
    public void findDevices() {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        List<Device> devices = new ArrayList<>();
        Device device;
        for (int i = 0; i < scanResults.size(); i++) {
            ScanResult scanresult = scanResults.get(i);
            device = new Device(scanresult.SSID, scanresult.BSSID, DeviceType.WIFI_NETWORK);
            devices.add(device);
        }

        finder.addDevices(devices);
    }
}
