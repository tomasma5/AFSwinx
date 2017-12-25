package cz.cvut.fel.matyapav.nearbytest.nearby.finder;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearby.model.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.nearby.util.NearbyConstants;
import cz.cvut.fel.matyapav.nearbytest.nearby.util.NearbyUtils;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class BTDevicesFinder extends AbstractNearbyDevicesFinder {

    private Activity activity;
    private BluetoothAdapter btAdapter;

    public BTDevicesFinder(Activity activity) {
        this.activity = activity;
        BluetoothManager btManager = (BluetoothManager) activity.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
    }

    @Override
    public void startFindingDevices() {
        if (btAdapter == null) {
            Toast.makeText(activity, NearbyConstants.BLUETOOTH_MISSING_MSG, Toast.LENGTH_SHORT).show();
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

    @Override
    public List<Device> stopFindingAndCollectDevices() {
        if (btAdapter.isDiscovering()) {
            btAdapter.cancelDiscovery();
        }
        return getFoundDevices();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Device device = new Device(bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.BLUETOOTH_DISCOVERED);
                device.addAdditionalInformation(NearbyConstants.ADDITIONAL_INFO_BT_MAJOR_CLASS,
                        NearbyUtils.getBluetoothDeviceType(bluetoothDevice
                                        .getBluetoothClass()
                                        .getMajorDeviceClass()
                        )
                );
                deviceFound(device);
            }
        }
    };
}


