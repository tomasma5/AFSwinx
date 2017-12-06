package cz.cvut.fel.matyapav.nearbytest;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.renderscript.Element;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class GetConnectedDevicesTask extends AsyncTask<Void, Void, List<Device>> {

    private WifiManager wifiManager;
    private MainActivity activity;
    private int pingTimeout = 100;

    GetConnectedDevicesTask(WifiManager wifiManager, MainActivity activity) {
        this.wifiManager = wifiManager;
        this.activity = activity;
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
                boolean reachable = address.isReachable(pingTimeout);
                if (reachable) {
                    Log.w(Constants.TAG, address.getCanonicalHostName() + " " + address.getHostAddress() + " " + DeviceType.WIFI_DEVICE);
                    connectedDevices.add(new Device(address.getCanonicalHostName(), address.getHostAddress(), DeviceType.WIFI_DEVICE));
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
        activity.addDevices(devices);
    }
}
