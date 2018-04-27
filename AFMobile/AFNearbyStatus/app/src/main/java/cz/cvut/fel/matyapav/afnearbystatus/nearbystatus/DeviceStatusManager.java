package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.miner.AbstractStatusMiner;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.miner.DeviceInfoMiner;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.task.DeviceStatusMinerTask;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.task.DeviceStatusEvent;

/**
 * Manages device status mining process
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class DeviceStatusManager {

    private Context context;
    private List<AbstractStatusMiner> minerList;
    private DeviceStatusMinerTask deviceStatusMinerTask;
    private DeviceStatus deviceStatus;

    DeviceStatusManager(Context context) {
        this.context = context;
        addStatusMiner(new DeviceInfoMiner()); //device info is mined ALWAYS
    }

    /**
     * Runs the device status mining process
     *
     * @param callbackClass object which implements {@link DeviceStatusEvent} class
     *                      - onDeviceStatusMined() method will be called at the end of mining
     */
    void mineDeviceStatus(DeviceStatusEvent callbackClass) {
        deviceStatusMinerTask = new DeviceStatusMinerTask(this, callbackClass);
        deviceStatusMinerTask.execute();
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * Adds status miner to miners list - if miner is added it will be considered during mining process
     *
     * @param statusMiner status miner
     */
    void addStatusMiner(AbstractStatusMiner statusMiner) {
        if (minerList == null) {
            minerList = new ArrayList<>();
        }
        statusMiner.setContext(context);
        minerList.add(statusMiner);
    }

    public List<AbstractStatusMiner> getMinerList() {
        return minerList;
    }

}
