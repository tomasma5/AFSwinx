package cz.cvut.fel.matyapav.nearbytest.Nearby.Finders;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;
import cz.cvut.fel.matyapav.nearbytest.Helpers.AppConstants;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Device;
import cz.cvut.fel.matyapav.nearbytest.Nearby.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Helpers.NearbyUtils;
import cz.cvut.fel.matyapav.nearbytest.Nearby.NearbyDevicesFinder;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class BTDevicesFinder implements INearbyDevicesFinder {

    private Activity activity;
    private BluetoothAdapter btAdapter;
    private NearbyDevicesFinder finder;

    public BTDevicesFinder(Activity activity, NearbyDevicesFinder finder) {
        this.activity = activity;
        this.finder = finder;
        BluetoothManager btManager = (BluetoothManager) activity.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
    }

    @Override
    public void findDevices() {
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

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i(AppConstants.APPLICATION_TAG, "Bluetooth Device found: " +
                        bluetoothDevice.getName() + "; MAC " +
                        bluetoothDevice.getAddress() +
                        " " +
                        NearbyUtils.getBluetoothDeviceType(bluetoothDevice.getBluetoothClass().getMajorDeviceClass()));
                Device device = new Device(bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.BLUETOOTH);
                finder.addDevice(device);
            }
        }
    };
}


