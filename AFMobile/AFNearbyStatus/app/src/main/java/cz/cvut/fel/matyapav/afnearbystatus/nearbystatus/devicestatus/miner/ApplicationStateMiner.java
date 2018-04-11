package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.miner;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.partial.ApplicationState;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public abstract class ApplicationStateMiner extends AbstractStatusMiner {

    public abstract String getUsername();

    public abstract String getAction();

    @Override
    public void mineAndFillStatus(DeviceStatus deviceStatus) {
        ApplicationState applicationState = new ApplicationState();
        applicationState.setUser(getUsername());
        applicationState.setAction(getAction());
        deviceStatus.setApplicationState(applicationState);
    }

}
