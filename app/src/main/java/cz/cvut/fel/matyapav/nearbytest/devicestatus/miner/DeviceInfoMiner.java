package cz.cvut.fel.matyapav.nearbytest.devicestatus.miner;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.devicestatus.model.partial.DeviceInfo;
import cz.cvut.fel.matyapav.nearbytest.util.AppConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class DeviceInfoMiner extends AbstractStatusMiner {

    private static final String WLAN_0 = "wlan0";

    public DeviceInfoMiner(Activity activity) {
        super(activity);
    }

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

    private String getMacAddress() {
        try {
            List<NetworkInterface> networkInterfaces;
            networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                if (!networkInterface.getName().equalsIgnoreCase(WLAN_0)) continue;
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
            Log.e(AppConstants.APPLICATION_TAG, "Cannot get mac address from network interfaces");
            ex.printStackTrace();
        }
        return null;
    }
}
