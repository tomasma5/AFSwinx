package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial;

/**
 * Wifi status model - keeps info about active wifi network, this model is part of {@link NetworkStatus} model
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class WifiStatus {

    private String bssid; //basic name of wifi network in form of mac addresss
    private String ssid; //name of wifi network
    private String ipAddress;

    public WifiStatus() {
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
