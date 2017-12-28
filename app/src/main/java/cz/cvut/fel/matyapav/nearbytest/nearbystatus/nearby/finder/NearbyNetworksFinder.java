package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.DeviceType;

/**
 * This nearby devices finder is responsible for finding nearby WIFI networks (access points)
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class NearbyNetworksFinder extends AbstractNearbyDevicesFinder {

    private boolean active;

    @Override
    public void startFindingDevices() {
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = wifiManager.getScanResults();
        Device device;
        active = true;
        for (int i = 0; i < scanResults.size(); i++) {
            if (!active) {
                break;
            }
            ScanResult scanresult = scanResults.get(i);
            device = new Device(scanresult.SSID, scanresult.BSSID, DeviceType.WIFI_NETWORK);
            deviceFound(device);
        }
    }

    @Override
    public List<Device> stopFindingAndCollectDevices() {
        this.active = false;
        return getFoundDevices();
    }


}
