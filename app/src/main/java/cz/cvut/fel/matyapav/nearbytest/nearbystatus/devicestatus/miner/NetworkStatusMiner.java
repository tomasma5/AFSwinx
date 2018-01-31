package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.NetworkStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.WifiStatus;

/**
 * This miner is resposible for getting information about active network - that means information
 * like if device is connected, network type and if its WIFI connection information about wifi network as well
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 *
 */
public class NetworkStatusMiner extends AbstractStatusMiner {

    private ConnectivityManager connectivityManager;

    @Override
    public void mineAndFillStatus(DeviceStatus deviceStatus){
        NetworkStatus networkStatus = new NetworkStatus();

        if(connectivityManager == null) {
            connectivityManager = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        //are we connected?
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        networkStatus.setConnected(isConnected);
        if(isConnected){
            networkStatus.setNetworkTypeName(activeNetwork.getTypeName());
            networkStatus.setNetworkSubtypeName(activeNetwork.getSubtypeName());
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            if(isWiFi){
                WifiStatus wifiStatus = mineWifiStatus();
                if(wifiStatus != null){
                    networkStatus.setWifiStatus(wifiStatus);
                }
            }
        }

        deviceStatus.setNetworkStatus(networkStatus);
    }

    /**
     * Gets wifi status - ssid, bssid, name and ipaddress;
     * @return wifi status
     */
    private WifiStatus mineWifiStatus(){
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if(wifiInfo != null) { //we are connected to some wifi

            WifiStatus wifiStatus = new WifiStatus();
            wifiStatus.setSsid(wifiInfo.getSSID());
            wifiStatus.setBssid(wifiInfo.getBSSID());
            @SuppressWarnings("deprecation") //WIFI info does not support ipv6 yet so deprecation does not make sense
                    String ipAddress = Formatter.formatIpAddress(wifiInfo.getIpAddress());
            wifiStatus.setIpAddress(ipAddress);
            return wifiStatus;
        }
        return null;
    }

}
