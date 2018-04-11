package model.afclassification;

import java.util.HashMap;
import java.util.Map;

/**
 * Common device model - same for all devices (bluetooth, wifi network, subnet device...)
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class NearbyDevice {

    private String name;
    private String macAddress;
    private String macVendor;
    private NearbyDeviceType deviceType;
    private Map<String, String> additionalInformations;

    public NearbyDevice() {
    }

    public NearbyDevice(String name, String address, NearbyDeviceType deviceType) {
        this.name = name;
        this.macAddress = address;
        this.deviceType = deviceType;
    }

    public String getName() {
        return name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public NearbyDeviceType getDeviceType() {
        return deviceType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setDeviceType(NearbyDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getMacVendor() {
        return macVendor;
    }

    public void setMacVendor(String macVendor) {
        this.macVendor = macVendor;
    }

    public void setAdditionalInformations(Map<String, String> additionalInformations) {
        this.additionalInformations = additionalInformations;
    }

    /**
     * Adds additional information about device;
     * @param informationName name of information as a key
     * @param informationContent conent of information as value
     */
    public void addAdditionalInformation(String informationName, String informationContent) {
        if(informationName == null || informationContent == null || informationName.isEmpty() || informationContent.isEmpty()){
            System.out.println("This information ["+informationName+":"+informationContent+"] is useless. Throwing it away...");
            return;
        }
        if(additionalInformations == null) {
            additionalInformations = new HashMap<>();
        }
        additionalInformations.put(informationName, informationContent);
    }

    public Map<String, String> getAdditionalInformations() {
        return additionalInformations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NearbyDevice device = (NearbyDevice) o;

        if (macAddress != null ? !macAddress.equals(device.macAddress) : device.macAddress != null)
            return false;
        return deviceType == device.deviceType;

    }

    @Override
    public int hashCode() {
        int result = macAddress != null ? macAddress.hashCode() : 0;
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        return result;
    }
}
