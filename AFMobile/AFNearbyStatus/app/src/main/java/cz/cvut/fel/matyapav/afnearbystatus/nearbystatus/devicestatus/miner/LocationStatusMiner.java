package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.miner;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.partial.LocationStatus;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.util.GlobalConstants;

/**
 * This miner is responsible for mining location of device - informations like longitude, latitude etc.
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class LocationStatusMiner extends AbstractStatusMiner implements LocationListener {

    private LocationManager locationManager;
    private DeviceStatus deviceStatus;

    @Override
    public void mineAndFillStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;

        locationManager = (LocationManager) getContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //if app doesn't have permissions
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(GlobalConstants.APPLICATION_TAG, "Application does not have ACCESS_FINE_LOCATION permission");
            return;
        }
        if(locationManager == null){
            Log.e(GlobalConstants.APPLICATION_TAG, "Location service could not be fetched.");
            return;
        }

        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            return;
        }
        Location location = null;

        //get more accurate location via GPS provider
        if (isGPSEnabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                this.deviceStatus.setLocationStatus(createLocationStatus(location));
            }
        }

        //if GPS was turned off or it was unable to get location try to get less accurate location with network provider
        if (location == null && isNetworkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    this.deviceStatus.setLocationStatus(createLocationStatus(location));
                }
        }
    }

    private LocationStatus createLocationStatus(Location location) {
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
