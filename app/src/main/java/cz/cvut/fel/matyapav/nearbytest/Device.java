package cz.cvut.fel.matyapav.nearbytest;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class Device {

    private String name;
    private String address;
    private DeviceType deviceType;

    public Device() {
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Device(String name, String address, DeviceType deviceType) {
        this.name = name;
        this.address = address;
        this.deviceType = deviceType;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
