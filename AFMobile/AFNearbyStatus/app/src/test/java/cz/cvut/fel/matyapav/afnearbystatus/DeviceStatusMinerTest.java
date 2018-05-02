package cz.cvut.fel.matyapav.afnearbystatus;

import android.Manifest;
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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class DeviceStatusMinerTest {

    private Context mMockContext;
    private DeviceStatus deviceStatus;

    @Before
    public void setUp() {
        mMockContext = mock(Context.class);
        deviceStatus = new DeviceStatus();
    }

    @Test
    public void mineBatteryInfoTest() {

        when(mMockContext.registerReceiver(ArgumentMatchers.isNull(), ArgumentMatchers.any(IntentFilter.class)))
                .thenReturn(new Intent(Intent.ACTION_BATTERY_CHANGED)
                        .putExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_CHARGING)
                        .putExtra(BatteryManager.EXTRA_LEVEL, 50)
                        .putExtra(BatteryManager.EXTRA_VOLTAGE, 4200)
                        .putExtra(BatteryManager.EXTRA_TEMPERATURE, 310)
                        .putExtra(BatteryManager.EXTRA_PLUGGED, BatteryManager.BATTERY_PLUGGED_AC))
        ;

        BatteryStatusMiner batteryStatusMiner = new BatteryStatusMiner();
        batteryStatusMiner.setContext(mMockContext);
        batteryStatusMiner.mineAndFillStatus(deviceStatus);

        assertNotNull(deviceStatus.getBatteryStatus());
        assertEquals(deviceStatus.getBatteryStatus().getBatteryLevel(), 50);
        assertEquals(deviceStatus.getBatteryStatus().isCharging(), true);
        assertEquals(deviceStatus.getBatteryStatus().getChargeType(), BatteryChargeType.AC);
        assertEquals(deviceStatus.getBatteryStatus().getVoltageLevel(), 4200);
        assertEquals(deviceStatus.getBatteryStatus().getTemperatureLevel(), 310);

    }

    @Test
    public void applicationStateMinerTest() {
        ApplicationStateMiner applicationStateMiner = new ApplicationStateMiner() {
            @Override
            public String getUsername() {
                return "username";
            }

            @Override
            public String getAction() {
                return "action";
            }
        };
        applicationStateMiner.mineAndFillStatus(deviceStatus);
        assertNotNull(deviceStatus.getApplicationState());
        assertEquals(deviceStatus.getApplicationState().getAction(), "action");
        assertEquals(deviceStatus.getApplicationState().getUser(), "username");
    }

    @Test
    public void networkStatusMinerWifiTest() {
        //setup
        ConnectivityManager connectivityManager = (ConnectivityManager) RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        ShadowNetworkInfo shadowNetworkInfo = Shadows.shadowOf(connectivityManager.getActiveNetworkInfo());

        shadowNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED);
        shadowNetworkInfo.setConnectionType(ConnectivityManager.TYPE_WIFI);
        shadowNetworkInfo.setSubType(0);
        shadowNetworkInfo.setAvailableStatus(true);

        WifiManager wifiManager = (WifiManager) RuntimeEnvironment.application.getSystemService(Context.WIFI_SERVICE);
        ShadowWifiInfo shadowWifiInfo = Shadows.shadowOf(wifiManager.getConnectionInfo());

        shadowWifiInfo.setSSID("SomeSSID");
        shadowWifiInfo.setBSSID("SomeBSSID");

        when(mMockContext.getApplicationContext()).thenReturn(mMockContext);
        when(mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);
        when(mMockContext.getSystemService(Context.WIFI_SERVICE)).thenReturn(wifiManager);

        NetworkStatusMiner networkStatusMiner = new NetworkStatusMiner();
        networkStatusMiner.setContext(mMockContext);
        networkStatusMiner.mineAndFillStatus(deviceStatus);
        assertNotNull(deviceStatus.getNetworkStatus());
        assertEquals(deviceStatus.getNetworkStatus().isConnected(), true);
        assertNotNull(deviceStatus.getNetworkStatus().getWifiStatus());
        assertEquals(deviceStatus.getNetworkStatus().getWifiStatus().getSsid(), "SomeSSID");
        assertEquals(deviceStatus.getNetworkStatus().getWifiStatus().getBssid(), "SomeBSSID");
    }

    @Test
    public void networkStatusMinerMobileTest() {
        //setup
        ConnectivityManager connectivityManager = (ConnectivityManager) RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        ShadowNetworkInfo shadowNetworkInfo = Shadows.shadowOf(connectivityManager.getActiveNetworkInfo());

        shadowNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED);
        shadowNetworkInfo.setConnectionType(ConnectivityManager.TYPE_MOBILE);
        shadowNetworkInfo.setSubType(0);
        shadowNetworkInfo.setAvailableStatus(true);

        when(mMockContext.getApplicationContext()).thenReturn(mMockContext);
        when(mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);

        NetworkStatusMiner networkStatusMiner = new NetworkStatusMiner();
        networkStatusMiner.setContext(mMockContext);
        networkStatusMiner.mineAndFillStatus(deviceStatus);
        assertNotNull(deviceStatus.getNetworkStatus());
        assertEquals(deviceStatus.getNetworkStatus().isConnected(), true);
        assertNull(deviceStatus.getNetworkStatus().getWifiStatus());
    }

    @Test
    public void networkStatusMinerNotConnectedTest() {
        //setup
        ConnectivityManager connectivityManager = (ConnectivityManager) RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        ShadowNetworkInfo shadowNetworkInfo = Shadows.shadowOf(connectivityManager.getActiveNetworkInfo());

        shadowNetworkInfo.setConnectionStatus(NetworkInfo.State.DISCONNECTED);

        when(mMockContext.getApplicationContext()).thenReturn(mMockContext);
        when(mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);

        NetworkStatusMiner networkStatusMiner = new NetworkStatusMiner();
        networkStatusMiner.setContext(mMockContext);
        networkStatusMiner.mineAndFillStatus(deviceStatus);
        assertEquals(deviceStatus.getNetworkStatus().isConnected(), false);
    }


    @Test
    public void deviceInfoMinerResolutionTest() {
        //setup
        WindowManager windowManager = (WindowManager) RuntimeEnvironment.application.getSystemService(Context.WINDOW_SERVICE);
        ShadowDisplay shadowDisplay = Shadows.shadowOf(windowManager.getDefaultDisplay());

        //actual values for Honor 8 device which has 5.2 inches display
        shadowDisplay.setWidth(1080);
        shadowDisplay.setHeight(1920);
        shadowDisplay.setDensityDpi(424);

        when(mMockContext.getApplicationContext()).thenReturn(mMockContext);
        when(mMockContext.getSystemService(Context.WINDOW_SERVICE)).thenReturn(windowManager);

        DeviceInfoMiner deviceInfoMiner = new DeviceInfoMiner();
        deviceInfoMiner.setContext(mMockContext);
        deviceInfoMiner.mineAndFillStatus(deviceStatus);
        assertNotNull(deviceStatus.getDeviceInfo());
        assertNotNull(deviceStatus.getDeviceInfo().getResolution());
        assertEquals(deviceStatus.getDeviceInfo().getResolution().getWidthInPixels(), 1080);
        assertEquals(deviceStatus.getDeviceInfo().getResolution().getHeightInPixels(), 1920);
        double inches = deviceStatus.getDeviceInfo().getResolution().getInches();
        inches = new BigDecimal(inches).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(inches, 5.2);
    }

    @Test
    public void locationMinerTestNoPermissions() {
        //setup
        LocationManager locationManager = (LocationManager) RuntimeEnvironment.application.getSystemService(Context.LOCATION_SERVICE);

        when(mMockContext.getApplicationContext()).thenReturn(mMockContext);
        when(mMockContext.getSystemService(Context.LOCATION_SERVICE)).thenReturn(locationManager);
        when(ActivityCompat.checkSelfPermission(mMockContext, Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(PackageManager.PERMISSION_DENIED);

        LocationStatusMiner locationStatusMiner = new LocationStatusMiner();
        locationStatusMiner.setContext(mMockContext);
        locationStatusMiner.mineAndFillStatus(deviceStatus);
        assertNull(deviceStatus.getLocationStatus());
    }

    @Test
    public void locationMinerTestProvidersBothDisabled() {
        //setup
        LocationManager locationManager = (LocationManager) RuntimeEnvironment.application.getSystemService(Context.LOCATION_SERVICE);

        ShadowLocationManager shadowLocationManager = Shadows.shadowOf(locationManager);
        shadowLocationManager.setProviderEnabled(LocationManager.GPS_PROVIDER, false);
        shadowLocationManager.setProviderEnabled(LocationManager.NETWORK_PROVIDER, false);

        when(mMockContext.getApplicationContext()).thenReturn(mMockContext);
        when(mMockContext.getSystemService(Context.LOCATION_SERVICE)).thenReturn(locationManager);
        when(ActivityCompat.checkSelfPermission(mMockContext, Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(PackageManager.PERMISSION_GRANTED);

        LocationStatusMiner locationStatusMiner = new LocationStatusMiner();
        locationStatusMiner.setContext(mMockContext);
        locationStatusMiner.mineAndFillStatus(deviceStatus);
        assertNull(deviceStatus.getLocationStatus());
    }

    @Test
    public void locationMinerTestNetworkProvider() {
        //setup
        LocationManager locationManager = (LocationManager) RuntimeEnvironment.application.getSystemService(Context.LOCATION_SERVICE);
        ShadowLocationManager shadowLocationManager = Shadows.shadowOf(locationManager);

        shadowLocationManager.setProviderEnabled(LocationManager.GPS_PROVIDER, false);
        shadowLocationManager.setProviderEnabled(LocationManager.NETWORK_PROVIDER, true);

        Location location = new Location(LocationManager.NETWORK_PROVIDER);
        location.setLatitude(50.23);
        location.setLongitude(18.47);
        location.setAltitude(250.44);
        location.setSpeed(3.8f);

        shadowLocationManager.setLastKnownLocation(LocationManager.NETWORK_PROVIDER, location);

        when(mMockContext.getApplicationContext()).thenReturn(mMockContext);
        when(mMockContext.getSystemService(Context.LOCATION_SERVICE)).thenReturn(locationManager);
        when(ActivityCompat.checkSelfPermission(mMockContext, Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(PackageManager.PERMISSION_GRANTED);

        LocationStatusMiner locationStatusMiner = new LocationStatusMiner();
        locationStatusMiner.setContext(mMockContext);
        locationStatusMiner.mineAndFillStatus(deviceStatus);
        assertNotNull(deviceStatus.getLocationStatus());
        assertEquals(deviceStatus.getLocationStatus().getLatitude(), 50.23);
        assertEquals(deviceStatus.getLocationStatus().getLongitude(), 18.47);
        assertEquals(deviceStatus.getLocationStatus().getAltitude(), 250.44);
        assertEquals(deviceStatus.getLocationStatus().getSpeed(), 3.8f, 0.02);
    }

    @Test
    public void locationMinerTestGPSProvider() {
        //setup
        LocationManager locationManager = (LocationManager) RuntimeEnvironment.application.getSystemService(Context.LOCATION_SERVICE);
        ShadowLocationManager shadowLocationManager = Shadows.shadowOf(locationManager);

        shadowLocationManager.setProviderEnabled(LocationManager.GPS_PROVIDER, true);
        shadowLocationManager.setProviderEnabled(LocationManager.NETWORK_PROVIDER, false);

        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(50.23);
        location.setLongitude(18.47);
        location.setAltitude(250.44);
        location.setSpeed(3.8f);

        shadowLocationManager.setLastKnownLocation(LocationManager.GPS_PROVIDER, location);

        when(mMockContext.getApplicationContext()).thenReturn(mMockContext);
        when(mMockContext.getSystemService(Context.LOCATION_SERVICE)).thenReturn(locationManager);
        when(ActivityCompat.checkSelfPermission(mMockContext, Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(PackageManager.PERMISSION_GRANTED);

        LocationStatusMiner locationStatusMiner = new LocationStatusMiner();
        locationStatusMiner.setContext(mMockContext);
        locationStatusMiner.mineAndFillStatus(deviceStatus);
        assertNotNull(deviceStatus.getLocationStatus());
        assertEquals(deviceStatus.getLocationStatus().getLatitude(), 50.23);
        assertEquals(deviceStatus.getLocationStatus().getLongitude(), 18.47);
        assertEquals(deviceStatus.getLocationStatus().getAltitude(), 250.44);
        assertEquals(deviceStatus.getLocationStatus().getSpeed(), 3.8f, 0.02);
    }
}