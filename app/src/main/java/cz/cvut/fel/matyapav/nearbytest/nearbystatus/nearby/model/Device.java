package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.enums.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class Device {

    private String name;
    private String macAddress;
    private DeviceType deviceType;
    private List<DeviceAdditionalInfo> additionalInformations;

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public Device(String name, String address, DeviceType deviceType) {
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

    public void addAdditionalInformation(String informationName, String informationContent) {
        if(informationName == null || informationContent == null || informationName.isEmpty() || informationContent.isEmpty()){
            Log.e(GlobalConstants.APPLICATION_TAG, "This information ["+informationName+":"+informationContent+"] is useless. Throwing it away...");
            return;
        }
        if(additionalInformations == null) {
            additionalInformations = new ArrayList<>();
        }
        additionalInformations.add(new DeviceAdditionalInfo(informationName, informationContent));
    }
    public List<DeviceAdditionalInfo> getAdditionalInformations() {
        return additionalInformations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

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
