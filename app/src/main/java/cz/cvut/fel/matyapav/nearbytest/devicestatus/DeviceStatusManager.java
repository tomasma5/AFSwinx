package cz.cvut.fel.matyapav.nearbytest.devicestatus;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.devicestatus.miner.AbstractStatusMiner;
import cz.cvut.fel.matyapav.nearbytest.devicestatus.model.DeviceStatus;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class DeviceStatusManager {

    private Activity activity;
    private List<AbstractStatusMiner> minerList;

    public DeviceStatusManager(Activity activity) {
        this.activity = activity;
    }

    public DeviceStatus getDeviceStatus(){
        DeviceStatus status = new DeviceStatus();
        //TODO do it in background???
        for (AbstractStatusMiner miner: minerList) {
            miner.mineAndFillStatus(status);
        }
        return status;
    }

    public DeviceStatusManager addStatusMiner(AbstractStatusMiner statusMiner){
        if(minerList == null){
            minerList = new ArrayList<>();
        }
        minerList.add(statusMiner);
        return this;
    }
}
