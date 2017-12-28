package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import android.app.Activity;
import android.util.Log;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.AbstractStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyStatusFacadeBuilder {

    private static NearbyStatusFacadeBuilder instance = null;

    private NearbyFinderManager nearbyFinderManager;
    private DeviceStatusManager deviceStatusManager;

    private NearbyStatusFacadeBuilder(){
    }

    public static synchronized NearbyStatusFacadeBuilder getInstance() {
        if(instance == null) {
            instance = new NearbyStatusFacadeBuilder();
        }
        return instance;
    }

    public NearbyStatusFacadeBuilder setRecommendedTimeout(int recommendedTimeout){
        try {
            nearbyFinderManager.setRecommendedTimeout(recommendedTimeout);
        } catch (NullPointerException npe) {
            logUninitializedManagerError();
            npe.printStackTrace();
            return null;
        }
        return this;
    }

    public NearbyStatusFacadeBuilder addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder, int batteryLevelLimit){
        try {
            nearbyFinderManager.addNearbyDevicesFinder(nearbyDevicesFinder, batteryLevelLimit);
        }  catch (NullPointerException npe) {
            logUninitializedManagerError();
            npe.printStackTrace();
            return null;
        }
        return this;
    }

    public NearbyStatusFacadeBuilder addNearbyDevicesFinder(AbstractNearbyDevicesFinder nearbyDevicesFinder){
        try {
            nearbyFinderManager.addNearbyDevicesFinder(nearbyDevicesFinder);
        }  catch (NullPointerException npe) {
            logUninitializedManagerError();
            npe.printStackTrace();
            return null;
        }
        return this;
    }


    public NearbyStatusFacadeBuilder addStatusMiner(AbstractStatusMiner statusMiner){
        try {
            deviceStatusManager.addStatusMiner(statusMiner);
        } catch (NullPointerException npe) {
            logUninitializedManagerError();
            npe.printStackTrace();
            return null;
        }
        return this;
    }

    public NearbyStatusFacadeBuilder initialize(Activity activity) {
        nearbyFinderManager = new NearbyFinderManager(activity);
        deviceStatusManager = new DeviceStatusManager(activity);
        return this;
    }

    public NearbyStatusFacade build() {
        return new NearbyStatusFacade(nearbyFinderManager, deviceStatusManager);
    }

    private void logUninitializedManagerError() {
        Log.e(GlobalConstants.APPLICATION_TAG, "Nearby finder manager was not initialized. You probably forgot to call initialize");
    }



}
