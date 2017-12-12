package cz.cvut.fel.matyapav.nearbytest;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class Device {

    private String name;
    private String macAddress;
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
        this.macAddress = address;
        this.deviceType = deviceType;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return macAddress.equals(device.macAddress);
    }

    @Override
    public int hashCode() {
        return macAddress.hashCode();
    }
}
