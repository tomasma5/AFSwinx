package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.miner;

import android.content.Context;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.DeviceStatus;

/**
 * Abstract template for all device status miners
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 *
 */
public abstract class AbstractStatusMiner {

    private Context context;

    /**
     * Gets desired status information from device and fills these information into {@link DeviceStatus} object
     * @param deviceStatus device status where the information should be filled in
     */
    public abstract void mineAndFillStatus(DeviceStatus deviceStatus);

    Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
