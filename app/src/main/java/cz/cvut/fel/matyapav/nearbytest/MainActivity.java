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
import android.widget.Toast;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.NearbyStatusFacadeBuilder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.BatteryStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.LocationStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.NetworkStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.BTBondedDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.BTDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.NearbyNetworksFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.SubnetDevicesFinder;

public class MainActivity extends AppCompatActivity {

    //permissions
    public static final int ACCESS_COARSE_LOCATION_PERMISSION_REQUEST = 1;
    public static final int ACCESS_FINE_LOCATION_PERMISSION_REQUEST = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ACCESS_COARSE_LOCATION is marked as dangerous permission and must be requested externally
        //TOTO leave permissions to framework user
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_COARSE_LOCATION_PERMISSION_REQUEST);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_PERMISSION_REQUEST);
        }

        Button getNearbyButton = (Button) findViewById(R.id.get_nearby_devices_btn);
        getNearbyButton.setOnClickListener(getNearbyDevices());
    }

    @NonNull
    private View.OnClickListener getNearbyDevices() {
        return view -> {
            NearbyStatusFacadeBuilder.getInstance()
                    .initialize(this)
                    .setRecommendedTimeout(10000)
                    .addNearbyDevicesFinder(new BTBondedDevicesFinder())
                    .addNearbyDevicesFinder(new BTDevicesFinder())
                    .addNearbyDevicesFinder(new NearbyNetworksFinder(), 20)
                    .addNearbyDevicesFinder(new SubnetDevicesFinder(), 30)
                    .addStatusMiner(new BatteryStatusMiner())
                    .addStatusMiner(new LocationStatusMiner())
                    .addStatusMiner(new NetworkStatusMiner())
                    .build()
                    .runProcess();
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case ACCESS_COARSE_LOCATION_PERMISSION_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "This app won't work without this permission", Toast.LENGTH_LONG).show();
                }
                break;
            case ACCESS_FINE_LOCATION_PERMISSION_REQUEST:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "This app won't work without this permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

}
