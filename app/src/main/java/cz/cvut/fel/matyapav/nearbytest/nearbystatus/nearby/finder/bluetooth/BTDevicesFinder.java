package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.BluetoothUtil;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.NearbyConstants;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;

import static cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.AdditionalInfoNames.*;

/**
 * This nearby devices finder finds reachable visible bluetooth devices in device surroundings
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class BTDevicesFinder extends AbstractNearbyDevicesFinder {

    private BluetoothAdapter btAdapter;
    private Set<BluetoothDevice> bondedDevices;

    @Override
    public void startFindingDevices() {
        BluetoothManager btManager = (BluetoothManager) getContext().getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        if (btAdapter == null) {
            Log.e(GlobalConstants.APPLICATION_TAG, NearbyConstants.BLUETOOTH_MISSING_MSG);
        } else {
            if (btAdapter.isEnabled()) {
                bondedDevices = btAdapter.getBondedDevices();
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                getContext().registerReceiver(mReceiver, filter);
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
                Device device = new Device(bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.BLUETOOTH);
                if(isBondedToDevice(bluetoothDevice)){
                    device.addAdditionalInformation(ADDITIONAL_INFO_BT_BONDED, Boolean.toString(true));
                }
                device.addAdditionalInformation(ADDITIONAL_INFO_BT_MAJOR_CLASS, BluetoothUtil.getBluetoothMajorDeviceClass(bluetoothDevice));
                device.addAdditionalInformation(ADDITIONAL_INFO_BT_CLASS, BluetoothUtil.getBluetoothDeviceClass(bluetoothDevice));
                deviceFound(device);
            }
        }
    };

    private boolean isBondedToDevice(BluetoothDevice btDevice){
        if(bondedDevices == null) {
            return false;
        }
        for(BluetoothDevice device : bondedDevices){
            if(device.getAddress().equals(btDevice.getAddress())){
                return true;
            }
        }
        return false;
    }
}


