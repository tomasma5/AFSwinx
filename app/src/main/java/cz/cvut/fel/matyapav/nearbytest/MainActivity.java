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
import cz.cvut.fel.matyapav.nearbytest.devicestatus.miner.NetworkStatusMiner;
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

        //ACCESS_COARSE_LOCATION is marked as dangerous permission and must be requested externally
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, AppConstants.ACCESS_COARSE_LOCATION_PERMISSION_REQUEST);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.ACCESS_FINE_LOCATION_PERMISSION_REQUEST);
        }

        deviceStatusManager = new DeviceStatusManager(this)
                .addStatusMiner(new BatteryStatusMiner(this))
                .addStatusMiner(new LocationStatusMiner(this))
                .addStatusMiner(new NetworkStatusMiner(this));

        //TODO set device status to view

        /*TODO udelat pak nejakeho defualt srace ktery bude poustet jak device status manager tak nearby manager
         a bude observovat kdy je to dokoncene a zabali v te metode ten objekt do jsonu a nekam posle
         api uz pro nej asi delat nebudu nebot chci aby obe veci byly pouzitelne zvlast a zvlast se nasetovaly
         takze do default srace uz budu posilat asi jen uz pripravene findery.. tohle je zvazim*/
        DeviceStatus deviceStatus = deviceStatusManager.getDeviceStatus();

        nearbyFinderManager = new NearbyFinderManager(this)
                .setRecommendedTimeout(10000)
                .setDeviceStatus(deviceStatus)
                .addNearbyDevicesFinder(new BTBondedDevicesFinder(this), 20)
                .addNearbyDevicesFinder(new BTDevicesFinder(this), 20)
                .addNearbyDevicesFinder(new NearbyNetworksFinder(this), 20)
                .addNearbyDevicesFinder(new SubnetDevicesFinder(this), 30);

        ListView listView = (ListView) findViewById(R.id.nearby_devices_list_view);
        listView.setAdapter(nearbyFinderManager.getAdapter());

        Button getNearbyButton = (Button) findViewById(R.id.get_nearby_devices_btn);
        getNearbyButton.setOnClickListener(getNearbyDevices());
    }

    @NonNull
    private View.OnClickListener getNearbyDevices() {
        return view -> nearbyFinderManager.findNearbyDevices();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppConstants.ACCESS_COARSE_LOCATION_PERMISSION_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "This app won't work without this permission", Toast.LENGTH_LONG).show();
                }
                break;
            case AppConstants.ACCESS_FINE_LOCATION_PERMISSION_REQUEST:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "This app won't work without this permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

}
