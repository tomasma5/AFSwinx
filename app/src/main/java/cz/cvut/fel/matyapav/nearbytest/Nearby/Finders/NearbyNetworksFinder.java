package cz.cvut.fel.matyapav.nearbytest.Nearby.Finders;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.Nearby.Device;
import cz.cvut.fel.matyapav.nearbytest.Nearby.DeviceType;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyNetworksFinder extends INearbyDevicesFinder {

    private WifiManager wifiManager;
    private boolean active = true;

    public NearbyNetworksFinder(Activity activity) {
        wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void startFindingDevices() {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        Device device;
        for (int i = 0; i < scanResults.size(); i++) {
            if(active) {
                ScanResult scanresult = scanResults.get(i);
                device = new Device(scanresult.SSID, scanresult.BSSID, DeviceType.WIFI_NETWORK);
                deviceFound(device);
            }
        }
    }

    @Override
    public List<Device> stopFindingAndCollectDevices() {
        this.active = false;
        return getFoundDevices();
    }


}
