package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.AbstractStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner.DeviceInfoMiner;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.task.DeviceStatusMinerTask;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.task.DeviceStatusVisitor;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class DeviceStatusManager {

    private Activity activity;
    private List<AbstractStatusMiner> minerList;
    private DeviceStatusMinerTask deviceStatusMinerTask;
    private DeviceStatus deviceStatus;

    public DeviceStatusManager(Activity activity) {
        this.activity = activity;
        addStatusMiner(new DeviceInfoMiner()); //device info is mined ALWAYS
    }

    public void mineDeviceStatus(DeviceStatusVisitor callbackClass){
        if(deviceStatusMinerTask == null){
            deviceStatusMinerTask = new DeviceStatusMinerTask(activity, this, callbackClass);
        }
         deviceStatusMinerTask.execute();
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void addStatusMiner(AbstractStatusMiner statusMiner){
        if(minerList == null){
            minerList = new ArrayList<>();
        }
        statusMiner.setActivity(activity);
        minerList.add(statusMiner);
    }

    public List<AbstractStatusMiner> getMinerList() {
        return minerList;
    }

}
