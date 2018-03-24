package cz.cvut.fel.matyapav.showcase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyScreenDefinition;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedLanguages;
import cz.cvut.fel.matyapav.afandroid.enums.uiproxy.Device;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.DeviceStatusAndNearbySearchEvent;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.NearbyStatusFacadeBuilder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.BatteryStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.LocationStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.NetworkStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.bluetooth.BTDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.network.NearbyNetworksFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.subnet.SubnetDevicesFinder;
import cz.cvut.fel.matyapav.showcase.fragments.LoginFragment;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;

public class MainActivity extends AppCompatActivity {

    //permissions
    public static final int ACCESS_FINE_LOCATION_PERMISSION_REQUEST = 28748;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().hasExtra("bundle") && savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras().getBundle("bundle");
        }
        super.onCreate(savedInstanceState);
        //set localization
        Localization.setPathToStrings("cz.cvut.fel.matyapav.showcase");

        if (savedInstanceState != null && Localization.getCurrentLanguage() != null) {
            Localization.changeLanguage(getApplicationContext(), Localization.getCurrentLanguage());
        }

        if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_PERMISSION_REQUEST);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        if (savedInstanceState == null) {
            getNearbyDevices();
            //set default login fragment
            try {
                ApplicationContext.getInstance().loadUIProxyUrl(getBaseContext());
                AFAndroid.getInstance().setApplicationContextUuid(ApplicationContext.getInstance().getUiProxyApplicationUuid(getBaseContext()));
                AFAndroid.getInstance().setDeviceType(Utils.deviceHasTabletSize(this) ? Device.TABLET : Device.PHONE);
            } catch (IOException e) {
                System.err.println("Cannot get properties file - so af android could not be properly configured");
                e.printStackTrace();
            }
            try {
                AFAndroidProxyScreenDefinition loginScreenDefinition = AFAndroid.getInstance()
                        .getScreenDefinitionBuilder(
                                getApplicationContext(),
                                ApplicationContext.getInstance().getUiProxyUrl() + "/api/screens/5a9955636402eb092c3b56c7", "Login")
                        .getScreenDefinition();
                LoginFragment loginFragment = new LoginFragment();
                loginFragment.setScreenDefinition(loginScreenDefinition);
                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.mainLayout, loginFragment);
                tx.commit();
            } catch (Exception e) {
                Log.e(MainActivity.class.getName(), "Cannot build login screen, for cause please check the stack trace.");
                ShowCaseUtils.showBuildingFailedDialog(this, e);
                e.printStackTrace();
            }
        } else {
            String currentFragmentUrl = getIntent().getStringExtra("current_fragment_proxy_url");
            String currentFragmentKey = getIntent().getStringExtra("current_fragment_proxy_key");
            ShowCaseUtils.refreshCurrentFragment(getThisActivity(), currentFragmentUrl, currentFragmentKey);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // LOCALIZATION
        int id = item.getItemId();
        if (id == R.id.langCZ) {
            Localization.changeLanguage(getApplicationContext(), SupportedLanguages.CZ);
            System.err.println("Current locale: " + getThisActivity().getResources().getConfiguration().locale);
            restartActivity();
        } else if (id == R.id.langEN) {
            Localization.changeLanguage(getApplicationContext(), SupportedLanguages.EN);
            System.err.println("Current locale: " + getThisActivity().getResources().getConfiguration().locale);
            restartActivity();
        }
        //do not call restart activity if new language was not set
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_PERMISSION_REQUEST:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "This app won't properly work without Fine location permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    public MainActivity getThisActivity() {
        return this;
    }

    private void restartActivity() {
        Intent intent = getIntent();
        Bundle temp_bundle = new Bundle();
        onSaveInstanceState(temp_bundle);
        intent.putExtra("bundle", temp_bundle);
        intent.putExtra("current_fragment_proxy_url", ApplicationContext.getInstance().getCurrentFragment().getScreenDefinition().getScreenUrl());
        intent.putExtra("current_fragment_proxy_key", ApplicationContext.getInstance().getCurrentFragment().getScreenDefinition().getKey());
        finish();
        startActivity(intent);
    }

    private boolean checkLocationPermission() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

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
                .executePeriodically(1000 * 60 * 5)
                .build()
                //.sendDataToServerAfterTimeout("http://192.168.100.8:8080/NSRest/api/consumer/add")
                //.sendDataToServerAfterTimeout("http://10.50.109.67:8080/NSRest/api/consumer/add")
                // .sendDataToServerAfterTimeout("http://147.32.217.40:8080/NSRest/api/consumer/add") //TODO uncomment this when we want ot actually store data
                .runProcess();
    }

}
