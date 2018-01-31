package cz.cvut.fel.matyapav.nearbytest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.StartBleScanRequest;

import java.util.concurrent.TimeUnit;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.DeviceStatusAndNearbySearchEvent;
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


    private GoogleApiClient mClient;
    private OnDataPointListener mListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ACCESS_COARSE_LOCATION is marked as dangerous permission and must be requested externally
        //TODO leave permissions to framework user
        //TODO correctly ask for permissions
        if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_PERMISSION_REQUEST);
        }
        if (!checkPermissionsBody()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BODY_SENSORS}, BODY_SENSORS_PERMISSIONS_REQUEST);
        }

        Button getNearbyButton = (Button) findViewById(R.id.get_nearby_devices_btn);
        getNearbyButton.setOnClickListener((View o) -> {
            getNearbyDevices();
            /// TODO Google Fit not working

            /*buildFitnessClient();
            buildBLE();
            if(mClient != null) {
                mClient.connect();
                findFitnessDataSources();
            }*/
        });
    }

    @NonNull
    private void getNearbyDevices() {
        NearbyStatusFacadeBuilder.getInstance()
                .initialize(getApplicationContext())
                .addStatusMiner(new BatteryStatusMiner())
                .addStatusMiner(new LocationStatusMiner())
                .addStatusMiner(new NetworkStatusMiner())
                .addNearbyDevicesFinder(new BTDevicesFinder())
                .addNearbyDevicesFinder(new NearbyNetworksFinder(), 20)
                .addNearbyDevicesFinder(new SubnetDevicesFinder(), 30)
                .setRecommendedTimeoutForNearbySearch(10000)
                .setNearbyDevicesSearchEvent(new DeviceStatusAndNearbySearchEvent() {
                    @Override
                    public void onSearchStart() {
                        Button btn = (Button) getActivity().findViewById(R.id.get_nearby_devices_btn);
                        btn.setEnabled(false);
                        btn.setBackgroundColor(getActivity().getResources().getColor(R.color.colorDisabled, null));
                    }

                    @Override
                    public void onSearchFinished() {
                        Button btn = (Button) getActivity().findViewById(R.id.get_nearby_devices_btn);
                        btn.setEnabled(true);
                        btn.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary, null));
                    }
                })
                .build()
                //.sendDataToServerAfterTimeout("http://192.168.100.8:8080/NSRest/api/consumer/add")
                .sendDataToServerAfterTimeout("http://10.50.109.67:8080/NSRest/api/consumer/add")
                .sendDataToServerAfterTimeout("http://147.32.217.40:8080/NSRest/api/consumer/add")
                .runProcess();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_PERMISSION_REQUEST:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "This app won't work without Fine location permission", Toast.LENGTH_LONG).show();
                }
                break;
            case BODY_SENSORS_PERMISSIONS_REQUEST:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "This app won't work without Body sensors permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    private boolean checkLocationPermission() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    private boolean checkPermissionsBody() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.BODY_SENSORS);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    ///////// FIT /////////

    private void buildFitnessClient() {
        if (mClient == null && checkLocationPermission() && checkPermissionsBody()) {

            mClient = new GoogleApiClient.Builder(this)
                    .addScope(new Scope(Scopes.FITNESS_BODY_READ))
                    .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                    .addApi(Fitness.SENSORS_API)
                    .addApi(Fitness.BLE_API)
                    .addConnectionCallbacks(
                            new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {
                                    Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Connected!!!");
                                    // Now you can make calls to the Fitness APIs.
                                }

                                @Override
                                public void onConnectionSuspended(int i) {
                                    if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                        Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Connection lost.  Cause: Network Lost.");
                                    } else if (i
                                            == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                        Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Connection lost.  Reason: Service Disconnected");
                                    }
                                }
                            }
                    )
                    .enableAutoManage(this, 0, connectionResult -> {
                        Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Google Play services connection failed. Cause: " +
                                connectionResult.toString());
                        Log.e(GlobalConstants.GOOGLE_FIT_TAG, "Exception while connecting to Google Play services: " +
                                connectionResult.getErrorMessage());
                    })
                    .build();
        }
    }

    private void findFitnessDataSources() {
        // [START find_data_sources]
        // Note: Fitness.SensorsApi.findDataSources() requires the ACCESS_FINE_LOCATION permission.
        Fitness.SensorsApi.findDataSources(mClient, new DataSourcesRequest.Builder()
                // At least one datatype must be specified.
                .setDataTypes(DataType.TYPE_HEART_RATE_BPM)
                // Can specify whether data type is raw or derived.
                //.setDataSourceTypes(DataSource.TYPE_RAW)
                .build())
                .setResultCallback(dataSourcesResult -> {
                    Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Result: " + dataSourcesResult.getStatus().toString());

                    for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                        Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Data source found: " + dataSource.toString());
                        Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Data Source type: " + dataSource.getDataType().getName());

                        //Let's register a listener to receive Activity data!
                        if (dataSource.getDataType().equals(DataType.TYPE_HEART_RATE_BPM)
                                && mListener == null) {
                            Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Data source for Heart Rate found!  Registering.");
                            registerFitnessDataListener(dataSource,
                                    DataType.TYPE_HEART_RATE_BPM);
                        }
                    }
                });
    }

    private void buildBLE() {
        BleScanCallback callback = new BleScanCallback() {
            @Override
            public void onDeviceFound(BleDevice device) {
                Log.d(GlobalConstants.GOOGLE_FIT_TAG, "Found bluetooth Device");
                Fitness.BleApi.claimBleDevice(mClient, device);
                Log.d(GlobalConstants.GOOGLE_FIT_TAG, "Claimed bluetooth Device");
            }

            @Override
            public void onScanStopped() {
                // The scan timed out or was interrupted
                Log.d(GlobalConstants.GOOGLE_FIT_TAG, "Scan was interruped");
            }

        };

        StartBleScanRequest request = new StartBleScanRequest.Builder()
                .setDataTypes(DataType.TYPE_HEART_RATE_BPM)
                .setBleScanCallback(callback)
                .build();

        if (mClient != null) {
            PendingResult<Status> pendingResult =
                    Fitness.BleApi.startBleScan(mClient, request);
            Log.d(GlobalConstants.GOOGLE_FIT_TAG, "Find Sources");
            Log.d(GlobalConstants.GOOGLE_FIT_TAG, "Pending result: " + pendingResult.toString());


        } else {
            Log.d(GlobalConstants.GOOGLE_FIT_TAG, "API client is null");
        }
    }

    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {
        Log.i(GlobalConstants.GOOGLE_FIT_TAG,"Listener Started");
        mListener = dataPoint -> {
            for (Field field : dataPoint.getDataType().getFields()) {
                Value val = dataPoint.getValue(field);
                Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Detected DataPoint field: " + field.getName());
                Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Detected DataPoint value: " + val);
            }
        };

        Fitness.SensorsApi.add(
                mClient,
                new SensorRequest.Builder()
                        .setDataSource(dataSource) // Optional but recommended for custom data sets.
                        .setDataType(dataType) // Can't be omitted.
                        .setSamplingRate(10, TimeUnit.SECONDS)
                        .build(),
                mListener)
                .setResultCallback(status -> {
                    if (status.isSuccess()) {
                        Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Listener registered!");
                    } else {
                        Log.i(GlobalConstants.GOOGLE_FIT_TAG, "Listener not registered.");
                    }
                });
    }

    public Activity getActivity() {
        return this;
    }
}
