package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.task.DeviceStatusVisitor;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.task.NearbyFinderVisitor;

/**
 * Facade for executing nearby devices finding and status mining processes
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyStatusFacade implements NearbyFinderVisitor, DeviceStatusVisitor{

    private NearbyFinderManager nearbyFinderManager;
    private DeviceStatusManager deviceStatusManager;
    private DeviceStatus deviceStatus;
    private List<Device> nearbyDevices;

    NearbyStatusFacade(NearbyFinderManager nearbyFinderManager, DeviceStatusManager deviceStatusManager) {
        this.nearbyFinderManager = nearbyFinderManager;
        this.deviceStatusManager = deviceStatusManager;
    }

    /**
     * Runs device status mining and nearby devices finding processes
     */
    public void runProcess() {
        deviceStatusManager.mineDeviceStatus(this);
    }

    @Override
    public void onDeviceStatusMined(){
        deviceStatus = deviceStatusManager.getDeviceStatus();
        nearbyFinderManager.filterNearbyFindersByDeviceStatus(deviceStatus);
        nearbyFinderManager.findNearbyDevices(this);
    }

    @Override
    public void onNearbyDevicesSearchFinished() {
        nearbyDevices = nearbyFinderManager.getFoundDevices();
        //TODO do some JSON serialization
    }
}
