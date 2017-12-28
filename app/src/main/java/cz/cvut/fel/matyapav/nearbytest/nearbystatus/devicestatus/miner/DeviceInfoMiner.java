package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner;

import android.os.Build;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.DeviceInfo;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;

import static cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.util.DeviceStatusConstants.NETWORK_INTERFACE_WLAN_0;

/**
 * This miner is responsible for getting basic device info like device api level, name, model etc.
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 *
 */
public class DeviceInfoMiner extends AbstractStatusMiner {

    @Override
    public void mineAndFillStatus(DeviceStatus deviceStatus) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setOsVersion(System.getProperty("os.version"));   // OS version
        deviceInfo.setApiLevel(Build.VERSION.SDK_INT);               // API Level
        deviceInfo.setDevice(android.os.Build.DEVICE);               // Device
        deviceInfo.setModel(android.os.Build.MODEL);                 // Model
        deviceInfo.setProduct(android.os.Build.PRODUCT);             //Product

        String macAddress = getMacAddress();
        if(macAddress != null){
            deviceInfo.setMacAddress(macAddress);
        }

        deviceStatus.setDeviceInfo(deviceInfo);
    }

    /**
     * Gets mac address of device from wlan0 network interface
     * @return mac address of device or null if something during process gone wrong
     */
    private String getMacAddress() {
        try {
            List<NetworkInterface> networkInterfaces;
            networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                if (!networkInterface.getName().equalsIgnoreCase(NETWORK_INTERFACE_WLAN_0)) continue;
                byte[] macBytes = new byte[0];
                macBytes = networkInterface.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }
                StringBuilder macAddressBuilder = new StringBuilder();
                for (byte b : macBytes) {
                    macAddressBuilder.append(Integer.toHexString(b & 0xFF)).append(":");
                }
                if (macAddressBuilder.length() > 0) {
                    macAddressBuilder.deleteCharAt(macAddressBuilder.length() - 1);
                }
                return macAddressBuilder.toString();
            }
        }catch (Exception ex){
            Log.e(GlobalConstants.APPLICATION_TAG, "Cannot get mac address from network interfaces");
            ex.printStackTrace();
        }
        return null;
    }
}
