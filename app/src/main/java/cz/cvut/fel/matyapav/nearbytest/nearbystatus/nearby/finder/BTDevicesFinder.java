package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.BluetoothUtil;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.NearbyConstants;

import static cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.AdditionalInfoNames.*;

/**
 * This nearby devices finder finds reachable visible bluetooth devices in device surroundings
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class BTDevicesFinder extends AbstractNearbyDevicesFinder {

    private BluetoothAdapter btAdapter;

    @Override
    public void startFindingDevices() {
        BluetoothManager btManager = (BluetoothManager) getActivity().getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        if (btAdapter == null) {
            Toast.makeText(getActivity(), NearbyConstants.BLUETOOTH_MISSING_MSG, Toast.LENGTH_SHORT).show();
        } else {
            if (btAdapter.isEnabled()) {
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                getActivity().registerReceiver(mReceiver, filter);
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

    /**
     * If device is found this receiver will register it
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Device device = new Device(bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.BLUETOOTH_DISCOVERED);
                device.addAdditionalInformation(ADDITIONAL_INFO_BT_MAJOR_CLASS, BluetoothUtil.getBluetoothMajorDeviceClass(bluetoothDevice));
                device.addAdditionalInformation(ADDITIONAL_INFO_BT_CLASS, BluetoothUtil.getBluetoothDeviceClass(bluetoothDevice));
                deviceFound(device);
            }
        }
    };
}


