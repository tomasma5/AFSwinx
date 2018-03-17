package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.subnet;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.NetworkUtils;

/**
 * This nearby devices finder is responsible for scan through wifi network the device is connected
 * and find devices connected to same wifi network. Finder should do it in parallel manner.
 * <p>
 * TODO E/LocationManager: thread is not runable, msg ignore, state:TERMINATED, pkg:cz.cvut.fel.matyapav.nearbytest
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class SubnetDevicesFinder extends AbstractNearbyDevicesFinder {

    private WifiManager wifiManager;
    private ExecutorService executor;
    private Set<String> addresses;
    private int noThreads = 255;
    private List<Future> submittedTasks;

    public SubnetDevicesFinder() {
        this.submittedTasks = new ArrayList<>();
        this.submittedTasks = new ArrayList<>();
        this.executor = Executors.newFixedThreadPool(noThreads);
    }

    @Override
    public void startFindingDevices() {
        wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled() && wifiManager.getConnectionInfo().getNetworkId() != -1) {
            prepareAddressForInspection();

            for (final String add : addresses) {
                Runnable worker = new SubnetDeviceFinderRunnable(add, this);
                submittedTasks.add(executor.submit(worker));
            }
            executor.shutdown();
        }
    }

    @Override
    public List<Device> stopFindingAndCollectDevices() {
        for (Future taskFuture : submittedTasks) {
            if(!taskFuture.isDone()) {
                taskFuture.cancel(true);
            }
        }
        executor.shutdownNow();
        submittedTasks.clear();
        return getFoundDevices();
    }

    public void setNoThreads(int noThreads) {
        this.noThreads = noThreads;
    }

    /**
     * Prepares addresses for inspection - firstly it get addresses from arp cache beacause those
     * will probably be reachable and then adds remaining ip addresses
     */
    private void prepareAddressForInspection() {
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        int ip = connectionInfo.getIpAddress();
        int mask = wifiManager.getDhcpInfo().netmask;
        int network = ip & mask;

        //WIFI info does not support ipv6 yet so deprecation does not make sense
        @SuppressWarnings("deprecation")
        String networkAddress = Formatter.formatIpAddress(network);
        @SuppressWarnings("deprecation")
        String maskk = Formatter.formatIpAddress(mask);
        @SuppressWarnings("deprecation")

        int range = NetworkUtils.getRangeFromMask(maskk);

        addresses = new HashSet<>();
        // Get addresses from ARP Info first as they are likely to be pingable
        addresses.addAll(NetworkUtils.getAllIPAddressesInARPCache());
        // Add all missing addresses in subnet
        String nextIpAddress = networkAddress;
        for (int j = 0; j < range; j++) {
            addresses.add(nextIpAddress);
            nextIpAddress = NetworkUtils.nextIpAddress(nextIpAddress);
        }
    }

}