package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public abstract class DeviceStatusAndNearbySearchEvent {

    public abstract void onSearchStart();

    public abstract void onSearchFinished();

}
