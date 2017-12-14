package cz.cvut.fel.matyapav.nearbytest.Nearby.Finders;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cz.cvut.fel.matyapav.nearbytest.Helpers.AppConstants;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Device;
import cz.cvut.fel.matyapav.nearbytest.Nearby.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Helpers.NearbyConstants;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Helpers.NearbyUtils;
import cz.cvut.fel.matyapav.nearbytest.Nearby.NearbyDevicesFinder;

public class SubnetDevicesFinder implements INearbyDevicesFinder {

    private WifiManager wifiManager;
    private int noThreads = 255;
    private int timeoutMillis = 2500;
    private ArrayList<String> addresses;
    private List<Device> devicesFound;
    private NearbyDevicesFinder finder;

    public SubnetDevicesFinder(Activity activity, NearbyDevicesFinder finder) {
        this.wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.finder = finder;
    }

    public void setNoThreads(int noThreads) throws IllegalArgumentException {
        if (noThreads < 1) {
            throw new IllegalArgumentException("Cannot have less than 1 thread");
        }
        this.noThreads = noThreads;
    }

    public void setTimeOutMillis(int timeOutMillis) throws IllegalArgumentException {
        if (timeOutMillis<0) {
            throw new IllegalArgumentException("Timeout cannot be less than 0");
        }
        this.timeoutMillis = timeOutMillis;
    }

    @Override
    public void findDevices() {
        if(wifiManager.isWifiEnabled() && wifiManager.getConnectionInfo().getNetworkId() != -1) {
            devicesFound = new ArrayList<>();
            prepareAddressFromInspection();
            ExecutorService executor = Executors.newFixedThreadPool(this.noThreads);

            for (final String add : addresses) {
                Runnable worker = new SubnetDeviceFinderRunnable(add);
                executor.execute(worker);
            }
            executor.shutdown();

            try {
                executor.awaitTermination(1, TimeUnit.HOURS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finder.addDevices(devicesFound);
        }
    }

    private void prepareAddressFromInspection() {
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        String ipAddress = Formatter.formatIpAddress(connectionInfo.getIpAddress()); //TODO deprecated
        addresses = new ArrayList<>();

        // Get addresses from ARP Info first as they are likely to be pingable
        addresses.addAll(NearbyUtils.getAllIPAddressesInARPCache());
        // Add all missing addresses in subnet
        String segment = ipAddress.substring(0, ipAddress.lastIndexOf(".") + 1);
        for (int j = 0; j < 255; j++) {
            if (!addresses.contains(segment + j)) {
                addresses.add(segment + j);
            }
        }
    }

    private synchronized void subnetDeviceFound(Device device){
        devicesFound.add(device);
    }

    private class SubnetDeviceFinderRunnable implements Runnable {
        private final String address;

        SubnetDeviceFinderRunnable(String address) {
            this.address = address;
        }

        @Override
        public void run() {
            try {
                InetAddress ia = InetAddress.getByName(address);
                boolean reachable = ia.isReachable(timeoutMillis);
                if (reachable){
                    String macAddress = NearbyUtils.getMacAddressFromIp(ia.getHostAddress());
                    if(!macAddress.equals(NearbyConstants.EMPTY_MAC_ADDRESS)) { //add only devices with mac address readable from ARP table
                        Log.w(AppConstants.APPLICATION_TAG, ia.getCanonicalHostName() + " " + macAddress + " " + DeviceType.WIFI_DEVICE);
                        subnetDeviceFound(new Device(ia.getCanonicalHostName(), macAddress, DeviceType.WIFI_DEVICE));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}