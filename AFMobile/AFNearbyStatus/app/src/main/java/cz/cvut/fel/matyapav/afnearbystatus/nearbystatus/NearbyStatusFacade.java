package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus;

import android.content.Context;
import android.os.Handler;

import java.sql.Timestamp;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.task.DeviceStatusEvent;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.model.DeviceStatusWithNearby;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.task.NearbyFinderEvent;

/**
 * Facade for executing nearby devices finding and status mining processes
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyStatusFacade implements NearbyFinderEvent, DeviceStatusEvent {

    private NearbyFinderManager nearbyFinderManager;
    private DeviceStatusManager deviceStatusManager;
    private DeviceStatusWithNearby deviceStatusWithNearby;
    private String serverUrl;
    private SearchEvent deviceStatusAndNearbySearchEvent;
    private boolean executePeriodically = false;
    private boolean alreadyRunning = false;
    private long periodicTime;
    private Handler runProcessHandler;

    private Runnable processRunnable = new Runnable() {
        @Override
        public void run() {
            alreadyRunning = true;
            runMining();
            if (executePeriodically) {
                runProcessHandler.postDelayed(this, periodicTime);
            }
        }
    };

    NearbyStatusFacade(NearbyFinderManager nearbyFinderManager,
                       DeviceStatusManager deviceStatusManager,
                       SearchEvent nearbyDevicesSearchEvent,
                       boolean executePeriodically, long periodicTime,
                       String serverUrl
    ) {
        this.nearbyFinderManager = nearbyFinderManager;
        this.deviceStatusManager = deviceStatusManager;
        this.deviceStatusAndNearbySearchEvent = nearbyDevicesSearchEvent;
        this.executePeriodically = executePeriodically;
        this.periodicTime = periodicTime;
        this.runProcessHandler = new Handler();
        this.serverUrl = serverUrl;
    }

    /**
     * Runs device status mining and nearby devices finding processes
     */
    public void runProcess() {
        if (alreadyRunning) {
            //cancel already running tasks and run again
            runProcessHandler.removeCallbacks(processRunnable);
        }
        runProcessHandler.post(processRunnable);
    }

    private void runMining() {
        if (deviceStatusAndNearbySearchEvent != null) {
            deviceStatusAndNearbySearchEvent.onSearchStart();
        }
        if (deviceStatusWithNearby == null) {
            deviceStatusWithNearby = new DeviceStatusWithNearby();
            //set timestamp to the beginning of process
            deviceStatusWithNearby.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        }
        deviceStatusManager.mineDeviceStatus(this);
    }

    @Override
    public void onDeviceStatusMined() {
        DeviceStatus deviceStatus = deviceStatusManager.getDeviceStatus();
        deviceStatusWithNearby.setDeviceStatus(deviceStatus);
        nearbyFinderManager.filterNearbyFindersByDeviceStatus(deviceStatus);
        nearbyFinderManager.findNearbyDevices(this);
    }

    @Override
    public void onNearbyDevicesSearchFinished() {
        deviceStatusWithNearby.setNearbyDevices(nearbyFinderManager.getFoundDevices());
        if (serverUrl != null) {
            new DataSenderTask(deviceStatusWithNearby, serverUrl).execute();
        }
        if (deviceStatusAndNearbySearchEvent != null) {
            deviceStatusAndNearbySearchEvent.onSearchFinished();
        }
    }

    /**
     * Gets found data as @{@link DeviceStatusWithNearby} wrapper
     *
     * @return found data
     */
    public DeviceStatusWithNearby getFoundData() {
        return deviceStatusWithNearby;
    }


}
