package cz.cvut.fel.matyapav.nearbytest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import cz.cvut.fel.matyapav.nearbytest.devicestatus.DeviceStatusManager;
import cz.cvut.fel.matyapav.nearbytest.devicestatus.miner.BatteryStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.devicestatus.miner.LocationStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.BTBondedDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.BTDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.NearbyNetworksFinder;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.SubnetDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.util.AppConstants;
import cz.cvut.fel.matyapav.nearbytest.nearby.NearbyFinderManager;

public class MainActivity extends AppCompatActivity {

    NearbyFinderManager nearbyFinderManager;
    DeviceStatusManager deviceStatusManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceStatusManager = new DeviceStatusManager(this)
                    .addStatusMiner(new BatteryStatusMiner(this))
                    .addStatusMiner(new LocationStatusMiner(this));

        DeviceStatus deviceStatus = deviceStatusManager.getDeviceStatus();

        nearbyFinderManager = new NearbyFinderManager(this)
                .setRecommendedTimeout(10000)
                .setDeviceStatus(deviceStatus)
                .addNearbyDevicesFinder(new BTBondedDevicesFinder(this), 20)
                .addNearbyDevicesFinder(new BTDevicesFinder(this), 20)
                .addNearbyDevicesFinder(new NearbyNetworksFinder(this), 20)
                .addNearbyDevicesFinder(new SubnetDevicesFinder(this), 30);

        //TODO set device status to view
        ListView listView = (ListView) findViewById(R.id.nearby_devices_list_view);
        listView.setAdapter(nearbyFinderManager.getAdapter());

        Button getNearbyButton = (Button) findViewById(R.id.get_nearby_devices_btn);
        getNearbyButton.setOnClickListener(getNearbyDevices());
    }

    @NonNull
    private View.OnClickListener getNearbyDevices() {
        return view -> {
            //ACCESS_COARSE_LOCATION is marked as dangerous permission and must be requested externally
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, AppConstants.ACCESS_COARSE_LOCATION_PERMISSION_REQUEST);
            } else {
                nearbyFinderManager.findNearbyDevices();
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppConstants.ACCESS_COARSE_LOCATION_PERMISSION_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    nearbyFinderManager.findNearbyDevices();
                } else {
                    //permission denied
                    Toast.makeText(this, "This app won't work without this permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

}
