package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.finder.network;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.model.DeviceType;

/**
 * This nearby devices finder is responsible for finding nearby WIFI networks (access points)
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class NearbyNetworksFinder extends AbstractNearbyDevicesFinder {

    private boolean active = false;

    @Override
    public void startFindingDevices() {
        active = true;
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager == null) {
            return;
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        if(scanResults == null) {
            return;
        }
        Device device;
        for (int i = 0; i < scanResults.size(); i++) {
            if(!active){
                return;
            }
            ScanResult scanresult = scanResults.get(i);
            device = new Device(scanresult.SSID, scanresult.BSSID, DeviceType.WIFI_NETWORK);
            deviceFound(device);
        }
    }

    @Override
    public List<Device> stopFindingAndCollectDevices() {
        active = false;
        return getFoundDevices();
    }

}
