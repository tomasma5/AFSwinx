package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.task;

/**
 * Callback interface for nearby status finding
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public interface NearbyFinderEvent {

    /**
     * Should be called when nearby devices finding process has finished
     */
    void onNearbyDevicesSearchFinished();
}
