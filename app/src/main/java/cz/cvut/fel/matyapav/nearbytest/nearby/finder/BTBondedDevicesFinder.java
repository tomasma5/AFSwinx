package cz.cvut.fel.matyapav.nearbytest.nearby.finder;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import cz.cvut.fel.matyapav.nearbytest.nearby.util.BluetoothUtil;
import cz.cvut.fel.matyapav.nearbytest.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearby.model.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.nearby.util.NearbyConstants;

import static cz.cvut.fel.matyapav.nearbytest.nearby.util.AdditionalInfoNames.*;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class BTBondedDevicesFinder extends AbstractNearbyDevicesFinder {

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
            return;
        }
        if (!btAdapter.isEnabled()) {
            return;
        }
        active = true;
        Set<BluetoothDevice> bondedDevices = btAdapter.getBondedDevices();
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (!active) {
                break;
            }
            Device device = new Device(bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.BLUETOOTH_BONDED);
            device.addAdditionalInformation(ADDITIONAL_INFO_BT_MAJOR_CLASS, BluetoothUtil.getBluetoothMajorDeviceClass(bluetoothDevice));
            device.addAdditionalInformation(ADDITIONAL_INFO_BT_CLASS, BluetoothUtil.getBluetoothDeviceClass(bluetoothDevice));
            deviceFound(device);

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
                Device device = new Device(bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.BLUETOOTH_DISCOVERED);
                deviceFound(device);
            }
        }
    };
}


