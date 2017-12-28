package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.DeviceStatusManager;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.task.DeviceStatusVisitor;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.NearbyFinderManager;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.task.NearbyFinderVisitor;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyStatus implements NearbyFinderVisitor, DeviceStatusVisitor{

    private NearbyFinderManager nearbyFinderManager;
    private DeviceStatusManager deviceStatusManager;
    private DeviceStatus deviceStatus;
    private List<Device> nearbyDevices;

    NearbyStatus(NearbyFinderManager nearbyFinderManager, DeviceStatusManager deviceStatusManager) {
        this.nearbyFinderManager = nearbyFinderManager;
        this.deviceStatusManager = deviceStatusManager;
    }

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
