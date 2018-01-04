package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import com.google.gson.Gson;

import java.sql.Timestamp;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.task.DeviceStatusVisitor;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.model.DeviceStatusWithNearby;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.task.NearbyFinderVisitor;

/**
 * Facade for executing nearby devices finding and status mining processes
 *  TODO add possibility to periodically run the service
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyStatusFacade implements NearbyFinderVisitor, DeviceStatusVisitor{

    private NearbyFinderManager nearbyFinderManager;
    private DeviceStatusManager deviceStatusManager;
    private DeviceStatusWithNearby deviceStatusWithNearby;

    NearbyStatusFacade(NearbyFinderManager nearbyFinderManager, DeviceStatusManager deviceStatusManager) {
        this.nearbyFinderManager = nearbyFinderManager;
        this.deviceStatusManager = deviceStatusManager;
    }

    /**
     * Runs device status mining and nearby devices finding processes
     */
    public void runProcess() {
        if(deviceStatusWithNearby == null){
            deviceStatusWithNearby = new DeviceStatusWithNearby();
            //set timestamp to the beginning of process
            deviceStatusWithNearby.setTimestamp(new Timestamp(System.currentTimeMillis()));
        }
        deviceStatusManager.mineDeviceStatus(this);
    }

    @Override
    public void onDeviceStatusMined(){
        DeviceStatus deviceStatus = deviceStatusManager.getDeviceStatus();
        deviceStatusWithNearby.setDeviceStatus(deviceStatus);
        nearbyFinderManager.filterNearbyFindersByDeviceStatus(deviceStatus);
        nearbyFinderManager.findNearbyDevices(this);
    }

    @Override
    public void onNearbyDevicesSearchFinished() {
        deviceStatusWithNearby.setNearbyDevices(nearbyFinderManager.getFoundDevices());
        String json = serializeDeviceStatusWithNearbyIntoJson();
        //TODO send json somewhere
    }

    private String serializeDeviceStatusWithNearbyIntoJson(){
        Gson gson = new Gson();
        return gson.toJson(deviceStatusWithNearby);
    }
}
