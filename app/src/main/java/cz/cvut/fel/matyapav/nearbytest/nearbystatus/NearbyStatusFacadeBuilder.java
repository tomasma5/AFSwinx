package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import android.content.Context;
import android.util.Log;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.AbstractStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;

/**
 * {@link NearbyStatusFacade} builder facade - it allows user to specify status miners and nearby
 * devices finders. As well user can specify timout of the process
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class NearbyStatusFacadeBuilder {

    private static NearbyStatusFacadeBuilder instance = null;
    private Context context;

    private NearbyFinderManager nearbyFinderManager;
    private DeviceStatusManager deviceStatusManager;
    private DeviceStatusAndNearbySearchEvent nearbyDevicesSearchEvent;

    //hide constructor of this class - singleton class should never be instantiated
    private NearbyStatusFacadeBuilder(){
    }

    /**
     * Gets instance of {@link NearbyStatusFacadeBuilder}
     * @return instnce of builder
     */
    public static synchronized NearbyStatusFacadeBuilder getInstance() {
        if(instance == null) {
            instance = new NearbyStatusFacadeBuilder();
        }
        return instance;
    }

    /**
     * Initializes builder - prepares all needed parts used in building process
     * Must be called right after getting instance of {@link NearbyStatusFacadeBuilder}!
     * @param context application context - it requires context
     * @return initialized instance of builder
     */
    public NearbyStatusFacadeBuilder initialize(Context context) {
        this.context = context;
        nearbyFinderManager = new NearbyFinderManager(context);
        deviceStatusManager = new DeviceStatusManager(context);
        return this;
    }

    /**
     * Sets recommended timeout of both nearby devices finding and status mining processes
     * @param recommendedTimeout recommended timout in milliseconds
     * @return updated instance of builder
     */
    public NearbyStatusFacadeBuilder setRecommendedTimeoutForNearbySearch(int recommendedTimeout){
        try {
            nearbyFinderManager.setRecommendedTimeout(recommendedTimeout);
        } catch (NullPointerException npe) {
            logUninitializedManagerError();
            npe.printStackTrace();
            return null;
        }
        return this;
    }

    /**
     * Adds nearby devices finder - if it is added it will be considered during nearby devices
     * finding process. It is also possible to specify battery level limit for specific finder. It
     * means the finder will not be used (turned off) if battery level is below this limit.
     *
     * @param nearbyDevicesFinder nearby devices finder
     * @param batteryLevelLimit battery level limitation
     * @return updated instance of builder
     */
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

    public NearbyStatusFacadeBuilder setNearbyDevicesSearchEvent(DeviceStatusAndNearbySearchEvent nearbyDevicesSearchEvent) {
        this.nearbyDevicesSearchEvent = nearbyDevicesSearchEvent;
        return this;
    }

    /**
     * Adds nearby devices finder - if it is added it will be considered during nearby devices
     * finding process.
     *
     * @param nearbyDevicesFinder nearby devices finder
     * @return updated instance of builder
     */
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

    /**
     * Adds status miner to miners list - if miner is added it will be considered during device
     * status mining process.
     * @param statusMiner status miner
     * @return updated instance of builder
     */
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

    /**
     * Builds nearby devices finding and device status mining processes execution {@link NearbyStatusFacade}
     * from properties previously set to {@link NearbyStatusFacadeBuilder}
     * @return execution facade
     */
    public NearbyStatusFacade build() {
        return new NearbyStatusFacade(context, nearbyFinderManager, deviceStatusManager, nearbyDevicesSearchEvent);
    }

    /**
     * Logs uninitialized {@link NearbyFinderManager} or {@link DeviceStatusManager} manager error
     */
    private void logUninitializedManagerError() {
        Log.e(GlobalConstants.APPLICATION_TAG, "Nearby finder manager was not initialized. You probably forgot to call initialize");
    }



}
