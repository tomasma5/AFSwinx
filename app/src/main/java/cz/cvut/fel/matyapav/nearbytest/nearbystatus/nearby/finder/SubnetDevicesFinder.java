package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder;

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
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.NearbyConstants;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.NearbyUtils;

/**
 * This nearby devices finder is responsible for scan through wifi network the device is connected
 * and find devices connected to same wifi network. Finder should do it in parallel manner.
 * <p>
 * TODO scanning only last 255 addresses - consult it with someone who is good in networks
 * TODO E/LocationManager: thread is not runable, msg ignore, state:TERMINATED, pkg:cz.cvut.fel.matyapav.nearbytest
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class SubnetDevicesFinder extends AbstractNearbyDevicesFinder {

    private WifiManager wifiManager;
    private int noThreads = 255;
    private int timeoutMillis = 60*1000;
    private ArrayList<String> addresses;
    private ExecutorService executor;
    private List<Future> submittedTasks;

    public SubnetDevicesFinder() {
        this.executor = Executors.newFixedThreadPool(this.noThreads);
        this.submittedTasks = new ArrayList<>();
    }

    /**
     * Sets number of threads which should process subnet addresses
     *
     * @param noThreads positive number of threads
     */
    public void setNoThreads(int noThreads) {
        if (noThreads < 1) {
            Log.e(GlobalConstants.APPLICATION_TAG, "Cannot have less than 1 thread");
            return;
        }
        this.noThreads = noThreads;
    }

    /**
     * Sets timeout in milliseconds - after how long all tasks should terminate
     * @param timeOutMillis
     */
    public void setTimeOutMillis(int timeOutMillis) {
        if (timeOutMillis < 0) {
            Log.e(GlobalConstants.APPLICATION_TAG, "Timeout cannot be less than 0");
            return;
        }
        this.timeoutMillis = timeOutMillis;
    }

    @Override
    public void startFindingDevices() {
        this.wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled() && wifiManager.getConnectionInfo().getNetworkId() != -1) {
            prepareAddressForInspection();

            for (final String add : addresses) {
                Runnable worker = new SubnetDeviceFinderRunnable(add);
                submittedTasks.add(executor.submit(worker));
            }
            executor.shutdown();
            try {
                executor.awaitTermination(60*1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Device> stopFindingAndCollectDevices() {
        for (Future taskFuture : submittedTasks) {
            if(!taskFuture.isDone()) {
                taskFuture.cancel(true);
            }
        }
        submittedTasks.clear();
        return getFoundDevices();
    }

    /**
     * Prepares addresses for inspection - firstly it get addresses from arp cache beacause those
     * will probably be reachable and then adds remaining ip addresses
     */
    private void prepareAddressForInspection() {
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        @SuppressWarnings("deprecation") //WIFI info does not support ipv6 yet so deprecation does not make sense
                String ipAddress = Formatter.formatIpAddress(connectionInfo.getIpAddress());
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

    /**
     * Called when subnet device is found
     *
     * @param device found device
     */
    private synchronized void subnetDeviceFound(Device device) {
        deviceFound(device);
    }

    /**
     * Runnable which checks if given ip address is reachable
     */
    private class SubnetDeviceFinderRunnable implements Runnable {
        private final String address;

        SubnetDeviceFinderRunnable(String address) {
            this.address = address;
        }

        @Override
        public void run() {
            try {
                InetAddress ia = InetAddress.getByName(address);
                boolean reachable = ia.isReachable(500);
                if (reachable) {
                    String macAddress = NearbyUtils.getMacAddressFromIp(ia.getHostAddress());
                    if (!macAddress.equals(NearbyConstants.EMPTY_MAC_ADDRESS)) { //add only devices with mac address readable from ARP table
                        Log.w(GlobalConstants.APPLICATION_TAG, ia.getCanonicalHostName() + " " + macAddress + " " + DeviceType.WIFI_DEVICE);
                        subnetDeviceFound(new Device(ia.getCanonicalHostName(), macAddress, DeviceType.WIFI_DEVICE));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}