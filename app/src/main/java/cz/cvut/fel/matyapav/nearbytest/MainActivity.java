package cz.cvut.fel.matyapav.nearbytest;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    List<Device> devices;
    NearbyDeviceListAdapter adapter;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i(Constants.TAG, "Bluetooth Device found: " +
                        device.getName() + "; MAC " +
                        device.getAddress() +
                        " " +
                        Utils.getBluetoothDeviceType(device.getBluetoothClass().getMajorDeviceClass()));
                Device d = new Device(device.getName(), device.getAddress(), DeviceType.BLUETOOTH);
                devices.add(d);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        Button getNearbyButton = (Button) findViewById(R.id.get_nearby_devices_btn);
        getNearbyButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.ACCESS_COARSE_LOCATION_PERMISSION_REQUEST);
            } else {
                getNearbyDevices();
            }
        });

    }

    private void initialize() {
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        btManager = (BluetoothManager) this.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        devices = new ArrayList<>();
        adapter = new NearbyDeviceListAdapter(this, devices);
        ListView listView = (ListView) findViewById(R.id.nearby_devices_list_view);
        listView.setAdapter(adapter);
    }

    private void getNearbyDevices() {
        devices.clear();
        //access points
        List<ScanResult> scanResults = wifiManager.getScanResults();
        Device device;
        for (int i = 0; i < scanResults.size(); i++) {
            ScanResult scanresult = scanResults.get(i);
            device = new Device(scanresult.SSID, scanresult.BSSID, DeviceType.WIFI_NETWORK);
            devices.add(device);
        }
        //bluetooth
        if (btAdapter == null) {
            Toast.makeText(this, "You don't have Bluetooth!", Toast.LENGTH_SHORT).show();
        } else {
            if (!btAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT);
            } else {
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mReceiver, filter);
                if (btAdapter.isDiscovering()) {
                    btAdapter.cancelDiscovery();
                }
                btAdapter.startDiscovery();
            }
        }
        //list of ip adresses
        new GetConnectedDevicesTask(wifiManager, this).execute();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.ACCESS_COARSE_LOCATION_PERMISSION_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getNearbyDevices();
                } else {
                    //permission denied
                    Toast.makeText(this, "This app won't work without this permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    private Activity getActivity() {
        return this;
    }

    public void addDevices(List<Device> devices) {
        this.devices.addAll(devices);
        adapter.notifyDataSetChanged();
    }
}
