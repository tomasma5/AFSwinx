package cz.cvut.fel.matyapav.nearbytest.nearby.finder;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import cz.cvut.fel.matyapav.nearbytest.util.AppConstants;
import cz.cvut.fel.matyapav.nearbytest.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearby.model.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.nearby.util.NearbyConstants;
import cz.cvut.fel.matyapav.nearbytest.nearby.util.NearbyUtils;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class BTBondedDevicesFinder extends INearbyDevicesFinder {

    private Activity activity;
    private BluetoothAdapter btAdapter;
    private boolean active;

    public BTBondedDevicesFinder(Activity activity) {
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
                active = true;
                Set<BluetoothDevice> bondedDevices = btAdapter.getBondedDevices();
                for (BluetoothDevice bluetoothDevice : bondedDevices) {
                    if (!active) {
                        break;
                    }
                    Device device = new Device(
                            bluetoothDevice.getName(),
                            bluetoothDevice.getAddress(),
                            DeviceType.BLUETOOTH_BONDED
                    );
                    device.addAdditionalInformation(
                            NearbyConstants.ADDITIONAL_INFO_BT_MAJOR_CLASS,
                            NearbyUtils.getBluetoothDeviceType(bluetoothDevice
                                    .getBluetoothClass()
                                    .getMajorDeviceClass()
                            )
                    );
                    deviceFound(device);

                }
            }
        }
    }

    @Override
    public List<Device> stopFindingAndCollectDevices() {
        active = false;
        return getFoundDevices();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                LogFoundDevice(bluetoothDevice);
                Device device = new Device(bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.BLUETOOTH_DISCOVERED);
                deviceFound(device);
            }
        }
    };

    private void LogFoundDevice(BluetoothDevice bluetoothDevice) {
        Log.i(AppConstants.APPLICATION_TAG, "Bluetooth Device found: " +
                bluetoothDevice.getName() + "; MAC " +
                bluetoothDevice.getAddress() +
                " " +
                NearbyUtils.getBluetoothDeviceType(bluetoothDevice.getBluetoothClass().getMajorDeviceClass()));
    }
}


