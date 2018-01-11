package cz.cvut.fel.matyapav.nearbytest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.NearbyStatusFacadeBuilder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.BatteryStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.LocationStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.NetworkStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.BTDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.NearbyNetworksFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.SubnetDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;

public class MainActivity extends AppCompatActivity {

    //permissions
    public static final int ACCESS_COARSE_LOCATION_PERMISSION_REQUEST = 1;
    public static final int ACCESS_FINE_LOCATION_PERMISSION_REQUEST = 2;
    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 3;
    private static final int BODY_SENSORS_PERMISSIONS_REQUEST = 4;

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BODY_SENSORS}, BODY_SENSORS_PERMISSIONS_REQUEST);
        }

        Button getNearbyButton = (Button) findViewById(R.id.get_nearby_devices_btn);
        getNearbyButton.setOnClickListener((o) -> {
            getNearbyDevices();
            ///
            FitnessOptions fitnessOptions = FitnessOptions.builder()
                    .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
                    .addDataType(DataType.AGGREGATE_HEART_RATE_SUMMARY, FitnessOptions.ACCESS_READ)
                    .build();

            if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
                GoogleSignIn.requestPermissions(
                        this, // your activity
                        GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                        GoogleSignIn.getLastSignedInAccount(this),
                        fitnessOptions);
            } else {
                accessGoogleFit();
            }
        });

    }

    @NonNull
    private void getNearbyDevices() {
        NearbyStatusFacadeBuilder.getInstance()
                .initialize(this)
                .setRecommendedTimeout(10000)
                .addNearbyDevicesFinder(new BTDevicesFinder())
                .addNearbyDevicesFinder(new NearbyNetworksFinder(), 20)
                .addNearbyDevicesFinder(new SubnetDevicesFinder(), 30)
                .addStatusMiner(new BatteryStatusMiner())
                .addStatusMiner(new LocationStatusMiner())
                .addStatusMiner(new NetworkStatusMiner())
                .build()
                .runProcess();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                accessGoogleFit();
            }
        }
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
            case BODY_SENSORS_PERMISSIONS_REQUEST:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "This app won't work without this permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    private void accessGoogleFit() {
        BleScanCallback bleScanCallbacks = new BleScanCallback() {
            @Override
            public void onDeviceFound(BleDevice device) {
                Log.i(GlobalConstants.APPLICATION_TAG, "DEVICE FOUND" + device.toString());
                // A device that provides the requested data types is available
            }

            @Override
            public void onScanStopped() {
                // The scan timed out or was interrupted
            }
        };

        //TODO check if it is not working only on my fitness band ... try to borrow xiaomi mi band or something like that and chceck if it can be found.
        Task<Void> response = Fitness.getBleClient(this,
                GoogleSignIn.getLastSignedInAccount(this))
                .startBleScan(Collections.singletonList(DataType.TYPE_STEP_COUNT_DELTA),
                        1000, bleScanCallbacks);
    }

}
