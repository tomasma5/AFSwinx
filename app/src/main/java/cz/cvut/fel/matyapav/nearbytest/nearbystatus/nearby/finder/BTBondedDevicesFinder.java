package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.BluetoothUtil;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.NearbyConstants;

import static cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.util.AdditionalInfoNames.*;

/**
 * This nearby devices finder is responsible for getting paired bluetooth devices
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class BTBondedDevicesFinder extends AbstractNearbyDevicesFinder {

    private boolean active;

    @Override
    public void startFindingDevices() {
        BluetoothManager btManager = (BluetoothManager) getActivity().getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter btAdapter = btManager.getAdapter();
        if (btAdapter == null) {
            Toast.makeText(getActivity(), NearbyConstants.BLUETOOTH_MISSING_MSG, Toast.LENGTH_SHORT).show();
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
            //TODO should I connect to the device and get its services - I can get more information but only sometimes - most of them are not relevant for our purpose
            //TODO at least i should connect to it to consider this nearby device
        }
    }


    @Override
    public List<Device> stopFindingAndCollectDevices() {
        active = false;
        return getFoundDevices();
    }
}


