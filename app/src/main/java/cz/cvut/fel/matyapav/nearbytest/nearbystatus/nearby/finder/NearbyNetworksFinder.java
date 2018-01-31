package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

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

    private NearbyNetworkFinderTask task;

    @Override
    public void startFindingDevices() {
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        task = new NearbyNetworkFinderTask(wifiManager, this);
        task.execute();
    }

    @Override
    public List<Device> stopFindingAndCollectDevices() {
        task.cancel(true);
        return getFoundDevices();
    }

    private static class NearbyNetworkFinderTask extends AsyncTask<Void, Void, Void> {

        private WifiManager wifiManager;
        private AbstractNearbyDevicesFinder finder;

        NearbyNetworkFinderTask(WifiManager wifiManager, AbstractNearbyDevicesFinder finder) {
            this.wifiManager = wifiManager;
            this.finder = finder;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(wifiManager == null) {
                return null;
            }
            List<ScanResult> scanResults = wifiManager.getScanResults();
            if(scanResults == null) {
                return null;
            }
            Device device;
            for (int i = 0; i < scanResults.size(); i++) {
                ScanResult scanresult = scanResults.get(i);
                device = new Device(scanresult.SSID, scanresult.BSSID, DeviceType.WIFI_NETWORK);
                finder.deviceFound(device);
            }
            return null;
        }
    }

}
