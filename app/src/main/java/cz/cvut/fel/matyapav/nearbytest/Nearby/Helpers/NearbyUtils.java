package cz.cvut.fel.matyapav.nearbytest.Nearby.Helpers;

import android.bluetooth.BluetoothClass;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.cvut.fel.matyapav.nearbytest.Helpers.AppConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyUtils {

    private NearbyUtils() {
    }

    public static String getBluetoothDeviceType(int bluetoothDeviceTypeInteger){
        switch (bluetoothDeviceTypeInteger){
            case BluetoothClass.Device.Major.AUDIO_VIDEO:
                return "AUDIO-VIDEO";
            case BluetoothClass.Device.Major.COMPUTER:
                return "COMPUTER";
            case BluetoothClass.Device.Major.HEALTH:
                return "HEALTH";
            case BluetoothClass.Device.Major.IMAGING:
                return "IMAGING";
            case BluetoothClass.Device.Major.MISC:
                return "MISC";
            case BluetoothClass.Device.Major.NETWORKING:
                return "NETWORKING";
            case BluetoothClass.Device.Major.PERIPHERAL:
                return "PERIPHERAL";
            case BluetoothClass.Device.Major.PHONE:
                return "PHONE";
            case BluetoothClass.Device.Major.TOY:
                return "TOY";
            case BluetoothClass.Device.Major.UNCATEGORIZED:
                return "UNCATEGORIZED";
            case BluetoothClass.Device.Major.WEARABLE:
                return "WEARABLE";
            default:
                return null;
        }
    }

    public static String getMacAddressFromIp(String ip) {
        String macAddress = NearbyConstants.EMPTY_MAC_ADDRESS;
        BufferedReader bufferedReader = null;
        try {
            if (ip != null) {
                String patternStr = String.format(NearbyConstants.MAC_REGULAR_EXPRESSION, ip.replace(".", "\\."));
                Pattern pattern = Pattern.compile(patternStr);
                bufferedReader = new BufferedReader(new FileReader(NearbyConstants.ARP_LOCATION), 8 * 1024);
                String line;
                Matcher matcher;
                while ((line = bufferedReader.readLine()) != null) {
                    matcher = pattern.matcher(line);
                    if (matcher.matches()) {
                        macAddress = matcher.group(1);
                        break;
                    }
                }
            } else {
                Log.e(AppConstants.APPLICATION_TAG, "ip is null");
            }
        } catch (IOException e) {
            Log.e(AppConstants.APPLICATION_TAG, "Can't open/read file ARP: " + e.getMessage());
            return macAddress;
        } finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                Log.e(AppConstants.APPLICATION_TAG, e.getMessage());
            }
        }
        return macAddress;
    }

}
