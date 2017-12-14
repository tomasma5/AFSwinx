package cz.cvut.fel.matyapav.nearbytest.Nearby.Tasks;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.Helpers.AppConstants;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Helpers.NearbyConstants;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Device;
import cz.cvut.fel.matyapav.nearbytest.Nearby.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Helpers.NearbyUtils;
import cz.cvut.fel.matyapav.nearbytest.NearbyDevicesFinder;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class ScanWifiNetworkForDevicesTask extends AsyncTask<Void, Void, List<Device>> {

    private NearbyDevicesFinder finder;
    private WifiManager wifiManager;

    public ScanWifiNetworkForDevicesTask(WifiManager wifiManager, NearbyDevicesFinder finder) {
        this.wifiManager = wifiManager;
        this.finder = finder;
    }

    @Override
    protected List<Device> doInBackground(Void... voids) {
        List<Device> connectedDevices = new ArrayList<>();
        try {
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            int ipAddress = connectionInfo.getIpAddress();
            String ipString = Formatter.formatIpAddress(ipAddress);

            String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);
            for (int i = 0; i < 255; i++) {
                String testIp = prefix + String.valueOf(i);

                InetAddress address = InetAddress.getByName(testIp);
                int pingTimeout = 100;
                boolean reachable = address.isReachable(pingTimeout);
                if (reachable){
                    String macAddress = NearbyUtils.getMacAddressFromIp(address.getHostAddress());
                    if(!macAddress.equals(NearbyConstants.EMPTY_MAC_ADDRESS)) { //add only devices with mac address readable from ARP table
                        Log.w(AppConstants.APPLICATION_TAG, address.getCanonicalHostName() + " " + macAddress + " " + DeviceType.WIFI_DEVICE);
                        connectedDevices.add(new Device(address.getCanonicalHostName(), macAddress, DeviceType.WIFI_DEVICE));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connectedDevices;
    }

    @Override
    protected void onPostExecute(List<Device> devices) {
        super.onPostExecute(devices);
        finder.addDevices(devices);
    }
}
