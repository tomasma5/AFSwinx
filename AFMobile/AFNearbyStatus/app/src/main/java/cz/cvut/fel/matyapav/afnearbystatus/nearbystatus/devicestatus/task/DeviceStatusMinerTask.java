package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.task;

import android.os.AsyncTask;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.DeviceStatusManager;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.DeviceStatus;

/**
 * Asynchronous task used fo getting information about device in background
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class DeviceStatusMinerTask extends AsyncTask<Void, Integer, Void> {

    private DeviceStatusManager deviceStatusManager;
    private DeviceStatusVisitor deviceStatusVisitor;

    public DeviceStatusMinerTask(DeviceStatusManager deviceStatusMiner, DeviceStatusVisitor deviceStatusVisitor) {
        this.deviceStatusManager = deviceStatusMiner;
        this.deviceStatusVisitor = deviceStatusVisitor;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DeviceStatus status = new DeviceStatus();
        deviceStatusManager.getMinerList().forEach(miner -> miner.mineAndFillStatus(status));
        deviceStatusManager.setDeviceStatus(status);
        deviceStatusVisitor.onDeviceStatusMined();
        return null;
    }

}
