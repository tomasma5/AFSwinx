package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.partial;

/**
 * Network status model - keeps information about device connectivity and active network
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class NetworkStatus {

    private boolean connected;
    private String networkTypeName; //wifi, mobile
    private String networkSubtypeName;
    private WifiStatus wifiStatus;

    public NetworkStatus() {
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getNetworkTypeName() {
        return networkTypeName;
    }

    public void setNetworkTypeName(String networkTypeName) {
        this.networkTypeName = networkTypeName;
    }

    public String getNetworkSubtypeName() {
        return networkSubtypeName;
    }

    public void setNetworkSubtypeName(String networkSubtypeName) {
        if(networkSubtypeName != null && !networkSubtypeName.isEmpty()) {
            this.networkSubtypeName = networkSubtypeName;
        }
    }

    public WifiStatus getWifiStatus() {
        return wifiStatus;
    }

    public void setWifiStatus(WifiStatus wifiStatus) {
        this.wifiStatus = wifiStatus;
    }
}
