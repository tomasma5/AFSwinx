package cz.cvut.fel.matyapav.nearbytest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.cvut.fel.matyapav.nearbytest.Adapters.NearbyDeviceListAdapter;
import cz.cvut.fel.matyapav.nearbytest.Helpers.Constants;
import cz.cvut.fel.matyapav.nearbytest.Helpers.Utils;
import cz.cvut.fel.matyapav.nearbytest.Tasks.ScanWifiNetworkForDevicesTask;


/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyDevicesFinder {

    private Activity activity;
    private WifiManager wifiManager;
    private BluetoothManager btManager;
    private BluetoothAdapter btAdapter;
    private Set<Device> devices;

    private ArrayAdapter adapter;


    public NearbyDevicesFinder(Activity activity) {
        this.activity = activity;
        wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        btManager = (BluetoothManager) activity.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        devices = new HashSet<>();
    }

    public void findNearbyDevices() {
        devices.clear();
        //access points
        getNearbyNetworks();
        //bluetooth
        getNearbyBluetoothDevices();
        //list of ip adresses
        getNearbyWifiDevices();
    }

    private void getNearbyNetworks() {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        Device device;
        for (int i = 0; i < scanResults.size(); i++) {
            ScanResult scanresult = scanResults.get(i);
            device = new Device(scanresult.SSID, scanresult.BSSID, DeviceType.WIFI_NETWORK);
            addDevice(device);
        }
    }

    private void getNearbyWifiDevices(){
        new ScanWifiNetworkForDevicesTask(wifiManager, this).execute();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i(Constants.TAG, "Bluetooth Device found: " +
                        bluetoothDevice.getName() + "; MAC " +
                        bluetoothDevice.getAddress() +
                        " " +
                        Utils.getBluetoothDeviceType(bluetoothDevice.getBluetoothClass().getMajorDeviceClass()));
                Device device = new Device(bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.BLUETOOTH);
                addDevice(device);
            }
        }
    };
    private void getNearbyBluetoothDevices(){
        if (btAdapter == null) {
            Toast.makeText(activity, "You don't have Bluetooth!", Toast.LENGTH_SHORT).show();
        } else {
            if (btAdapter.isEnabled()) {
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                activity.registerReceiver(mReceiver, filter);
                if (btAdapter.isDiscovering()) {
                    btAdapter.cancelDiscovery();
                }
                btAdapter.startDiscovery();
            }
        }
    }


    public void addDevices(List<Device> devices) {
        this.devices.addAll(devices);
        if(adapter != null){
            adapter.notifyDataSetChanged();
        } else {
            System.err.println("Cannot update adapter collection - maybe you forgot to set it?");
        }
    }

    public void addDevice(Device device){
        devices.add(device);
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            System.err.println("Cannot update adapter collection - maybe you forgot to set it?");
        }
    }

    public Set<Device> getFoundDevices() {
        return devices;
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }
}
