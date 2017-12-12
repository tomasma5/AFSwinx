package cz.cvut.fel.matyapav.nearbytest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import cz.cvut.fel.matyapav.nearbytest.Helpers.Constants;

public class MainActivity extends AppCompatActivity {

    NearbyDevicesFinder nearbyDevicesFinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nearbyDevicesFinder = new NearbyDevicesFinder(this);
        ListView listView = (ListView) findViewById(R.id.nearby_devices_list_view);
        listView.setAdapter(nearbyDevicesFinder.getAdapter());

        Button getNearbyButton = (Button) findViewById(R.id.get_nearby_devices_btn);
        getNearbyButton.setOnClickListener(view -> {
            //ACCESS_COARSE_LOCATION is marked as dangerous permission and must be requested externally
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.ACCESS_COARSE_LOCATION_PERMISSION_REQUEST);
            } else {
                nearbyDevicesFinder.findNearbyDevices();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.ACCESS_COARSE_LOCATION_PERMISSION_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    nearbyDevicesFinder.findNearbyDevices();
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
