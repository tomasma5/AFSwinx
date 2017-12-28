package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import android.app.Activity;
import android.util.Log;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.DeviceStatusManager;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.AbstractStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.NearbyFinderManager;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.Constants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyStatusBuilder {

    private static NearbyStatusBuilder instance = null;

    private NearbyFinderManager nearbyFinderManager;
    private DeviceStatusManager deviceStatusManager;

    private NearbyStatusBuilder(){
    }

    public static synchronized NearbyStatusBuilder getInstance() {
        if(instance == null) {
            instance = new NearbyStatusBuilder();
        }
        return instance;
    }

    public NearbyStatusBuilder setRecommendedTimeout(int recommendedTimeout){
        try {
            nearbyFinderManager.setRecommendedTimeout(recommendedTimeout);
        } catch (NullPointerException npe) {
            logUninitializedManagerError();
            npe.printStackTrace();
            return null;
        }
        return this;
    }

    public NearbyStatusBuilder addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder, int batteryLevelLimit){
        try {
            nearbyFinderManager.addNearbyDevicesFinder(nearbyDevicesFinder, batteryLevelLimit);
        }  catch (NullPointerException npe) {
            logUninitializedManagerError();
            npe.printStackTrace();
            return null;
        }
        return this;
    }

    public NearbyStatusBuilder addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder){
        try {
            nearbyFinderManager.addNearbyDevicesFinder(nearbyDevicesFinder);
        }  catch (NullPointerException npe) {
            logUninitializedManagerError();
            npe.printStackTrace();
            return null;
        }
        return this;
    }


    public NearbyStatusBuilder addStatusMiner(AbstractStatusMiner statusMiner){
        try {
            deviceStatusManager.addStatusMiner(statusMiner);
        } catch (NullPointerException npe) {
            logUninitializedManagerError();
            npe.printStackTrace();
            return null;
        }
        return this;
    }

    public NearbyStatusBuilder initialize(Activity activity) {
        nearbyFinderManager = new NearbyFinderManager(activity);
        deviceStatusManager = new DeviceStatusManager(activity);
        return this;
    }

    public NearbyStatus build() {
        return new NearbyStatus(nearbyFinderManager, deviceStatusManager);
    }

    private void logUninitializedManagerError() {
        Log.e(Constants.APPLICATION_TAG, "Nearby finder manager was not initialized. You probably forgot to call initialize");
    }



}
