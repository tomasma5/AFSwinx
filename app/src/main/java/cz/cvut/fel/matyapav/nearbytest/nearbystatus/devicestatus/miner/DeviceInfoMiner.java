package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.DeviceInfo;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.Resolution;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.NetworkUtils;

/**
 * This miner is responsible for getting basic device info like device api level, name, model etc.
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class DeviceInfoMiner extends AbstractStatusMiner {

    @Override
    public void mineAndFillStatus(DeviceStatus deviceStatus) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setOsVersion(System.getProperty("os.version"));   // OS version
        deviceInfo.setApiLevel(Build.VERSION.SDK_INT);               // API Level
        deviceInfo.setDevice(android.os.Build.DEVICE);               // Device
        deviceInfo.setModel(android.os.Build.MODEL);                 // Model
        deviceInfo.setProduct(android.os.Build.PRODUCT);             // Product
        deviceInfo.setResolution(getResolution());                   // Resolution
        String macAddress = NetworkUtils.getMacAddress();            // Mac address
        if (macAddress != null) {
            deviceInfo.setMacAddress(macAddress);
        }
        deviceStatus.setDeviceInfo(deviceInfo);
    }

    private Resolution getResolution() {
        Resolution resolution = new Resolution();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (windowManager == null) {
            return null;
        }
        windowManager.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double) width / (double) dens;
        double hi = (double) height / (double) dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);
        resolution.setWidthInPixels(width);
        resolution.setHeightInPixels(height);
        resolution.setInches(screenInches);
        return resolution;
    }


}
