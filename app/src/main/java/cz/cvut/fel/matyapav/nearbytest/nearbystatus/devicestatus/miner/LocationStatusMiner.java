package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.Calendar;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.LocationStatus;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

//TODO not working ....
public class LocationStatusMiner extends AbstractStatusMiner implements LocationListener {

    private LocationManager locationManager;
    private DeviceStatus deviceStatus;

    @Override
    public void mineAndFillStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
        //TODO

        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        int twoMinutesInMillis = 2 * 60 * 1000;
        if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - twoMinutesInMillis) {
            this.deviceStatus.setLocationStatus(createLocationStatus(location));
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    private LocationStatus createLocationStatus(Location location){
        LocationStatus locationStatus = new LocationStatus();

        locationStatus.setLatitude(location.getLatitude());
        locationStatus.setLongitude(location.getLongitude());
        locationStatus.setAltitude(location.getAltitude());
        locationStatus.setSpeed(location.getSpeed());

        return locationStatus;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            this.deviceStatus.setLocationStatus(createLocationStatus(location));
            locationManager.removeUpdates(this);
        }
    }

    //must be implemented

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }
}
