package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.util.GlobalConstants;

import static cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.util.DeviceStatusConstants.NETWORK_INTERFACE_WLAN_0;

/**
 * Util methods used during nearby devices finding process
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class NetworkUtils{


    public static String nextIpAddress(final String input) {
        checkIpAddressFormat(input);
        final String[] tokens = input.split("\\.");
        for (int i = tokens.length - 1; i >= 0; i--) {
            final int item = Integer.parseInt(tokens[i]);
            if (item > 255 || item < 0) {
                throw new IllegalArgumentException("One of IP parts contains number which is not in limits");
            }
            if (item < 255) {
                tokens[i] = String.valueOf(item + 1);
                for (int j = i + 1; j < 4; j++) {
                    tokens[j] = "0";
                }
                break;
            }

        }
        return tokens[0] + '.' +
                tokens[1] + '.' +
                tokens[2] + '.' +
                tokens[3];
    }

    public static void checkIpAddressFormat(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new IllegalArgumentException("Ip address is null or empty");
        }
        final String[] tokens = ipAddress.split("\\.");
        if (tokens.length != 4) {
            throw new IllegalArgumentException("IP address has bad format");
        }
        for (String token : tokens) {
            if (token.isEmpty()) {
                throw new IllegalArgumentException("One of IP parts are missing");
            }
            try {
                final int item = Integer.parseInt(token);
                if (item > 255 || item < 0) {
                    throw new IllegalArgumentException("One of IP parts contains number which is not in limits");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("One of IP parts does not contain number");
            }
        }
    }

    public static int getRangeFromMask(String maskAddress) {
        checkIpAddressFormat(maskAddress);
        String[] parts = maskAddress.split("\\.");
        int[] ranges = new int[parts.length];
        int i = 0;
        for (String part : parts) {
            int value = Integer.parseInt(part);
            ranges[i] = (255 - value) + 1;
            i++;
        }
        int result = 1;
        for (int range : ranges) {
            result *= range;
        }
        return result - 2; //first is network address, last is broadcast address
    }

    /**
     * Gets mac address from ip address
     *
     * @param ip ip address
     * @return mac address
     */
    public static String getMacAddressFromIp(String ip) {
        String macAddress = NearbyConstants.EMPTY_MAC_ADDRESS;
        if (ip != null) {
            String patternStr = String.format(NearbyConstants.MAC_REGULAR_EXPRESSION, ip.replace(".", "\\."));
            Pattern pattern = Pattern.compile(patternStr);

            Matcher matcher;
            for (String line : getLinesInARPCache()) {
                matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    macAddress = matcher.group(1);
                    break;
                }
            }
        } else {
            Log.e(GlobalConstants.APPLICATION_TAG, "ip is null");
        }
        return macAddress;
    }

    /**
     * Returns all the ip addresses currently in the ARP cache (/proc/net/arp).
     *
     * @return list of IP addresses found
     */
    public static Set<String> getAllIPAddressesInARPCache() {

        return getAllIPAndMACAddressesInARPCache().keySet();
    }

    /**
     * Gets all ip and mac addresses from ARP Cache
     *
     * @return hashmap of pairs - ip address, mac address from ARP cache
     */
    private static Map<String, String> getAllIPAndMACAddressesInARPCache() {
        Map<String, String> macMap = new HashMap<>();
        for (String line : getLinesInARPCache()) {
            String[] splitted = line.split(" +");
            if (splitted.length >= 4) {
                // Ignore values with invalid MAC addresses
                if (splitted[3].matches("..:..:..:..:..:..")
                        && !splitted[3].equals("00:00:00:00:00:00")) {
                    macMap.put(splitted[0], splitted[3]);
                }
            }
        }
        return macMap;
    }

    /**
     * Method to read lines from the ARP Cache
     *
     * @return the lines of the ARP Cache.
     */
    private static ArrayList<String> getLinesInARPCache() {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(NearbyConstants.ARP_LOCATION), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            Log.e(GlobalConstants.APPLICATION_TAG, "Can't open/read file ARP: " + e.getMessage());
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Gets mac address of device from wlan0 network interface
     *
     * @return mac address of device or null if something during process gone wrong
     */
    public static String getMacAddress() {
        try {
            List<NetworkInterface> networkInterfaces;
            networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                if (!networkInterface.getName().equalsIgnoreCase(NETWORK_INTERFACE_WLAN_0))
                    continue;
                byte[] macBytes = networkInterface.getHardwareAddress();
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
        } catch (Exception ex) {
            Log.e(GlobalConstants.APPLICATION_TAG, "Cannot get mac address from network interfaces");
            ex.printStackTrace();
        }
        return null;
    }

}
