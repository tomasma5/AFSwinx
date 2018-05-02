package cz.cvut.fel.matyapav.afnearbystatus;

import android.Manifest;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.support.v4.app.ActivityCompat;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowBluetoothAdapter;
import org.robolectric.shadows.ShadowDisplay;
import org.robolectric.shadows.ShadowLocationManager;
import org.robolectric.shadows.ShadowNetworkInfo;
import org.robolectric.shadows.ShadowWifiInfo;

import java.math.BigDecimal;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.miner.ApplicationStateMiner;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.miner.BatteryStatusMiner;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.miner.DeviceInfoMiner;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.miner.LocationStatusMiner;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.miner.NetworkStatusMiner;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.partial.BatteryChargeType;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.finder.bluetooth.BTDevicesFinder;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class NearbyFinderTest {

    private Context mMockContext;

    @Before
    public void setUp() {
        mMockContext = mock(Context.class);
    }

    @Test
    public void bluetoothFinderBluetoothDisabledTest() {
        //setup
        BluetoothManager bluetoothManager = (BluetoothManager) RuntimeEnvironment.application.getSystemService(Context.BLUETOOTH_SERVICE);
        ShadowBluetoothAdapter shadowBluetoothAdapter = Shadows.shadowOf(bluetoothManager.getAdapter());

        shadowBluetoothAdapter.setEnabled(false);

        when(mMockContext.getApplicationContext()).thenReturn(mMockContext);
        when(mMockContext.getSystemService(Context.BLUETOOTH_SERVICE)).thenReturn(bluetoothManager);

        BTDevicesFinder devicesFinder = new BTDevicesFinder();
        devicesFinder.setContext(mMockContext);
        devicesFinder.startFindingDevices();
        assertEquals(bluetoothManager.getAdapter().isDiscovering(), false);
    }

    @Test
    public void bluetoothFinderBluetoothEnabledTest() {
        //setup
        BluetoothManager bluetoothManager = (BluetoothManager) RuntimeEnvironment.application.getSystemService(Context.BLUETOOTH_SERVICE);
        ShadowBluetoothAdapter shadowBluetoothAdapter = Shadows.shadowOf(bluetoothManager.getAdapter());

        shadowBluetoothAdapter.setEnabled(true);

        when(mMockContext.getApplicationContext()).thenReturn(mMockContext);
        when(mMockContext.getSystemService(Context.BLUETOOTH_SERVICE)).thenReturn(bluetoothManager);

        BTDevicesFinder devicesFinder = new BTDevicesFinder();
        devicesFinder.setContext(mMockContext);
        devicesFinder.startFindingDevices();
        assertEquals(bluetoothManager.getAdapter().isDiscovering(), true);
        devicesFinder.stopFindingAndCollectDevices();
        assertEquals(bluetoothManager.getAdapter().isDiscovering(), false);
    }
}