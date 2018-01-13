package model.partial;

import java.util.Map;

/**
 * Common device model - same for all devices (bluetooth, wifi network, subnet device...)
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public class Device {

    private String name;
    private String macAddress;
    private DeviceType deviceType;
    private Map<String, String> additionalInformations;

    public Device() {
    }

    public String getName() {
        return name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public Map<String, String> getAdditionalInformations() {
        return additionalInformations;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public void setAdditionalInformations(Map<String, String> additionalInformations) {
        this.additionalInformations = additionalInformations;
    }
}
