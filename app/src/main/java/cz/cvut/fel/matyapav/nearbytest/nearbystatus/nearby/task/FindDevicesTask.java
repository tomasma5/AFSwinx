package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.task;

import android.os.AsyncTask;

import java.util.Timer;
import java.util.TimerTask;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.NearbyFinderManager;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;

import static com.google.android.gms.internal.zzahn.runOnUiThread;

/**
 * Asynchronous task for finding nearby devices in background
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class FindDevicesTask extends AsyncTask<Void, Integer, Void> {

    private NearbyFinderManager finder;
    private NearbyFinderVisitor nearbyFinderVisitor;
    private int timeoutInMillis = 12000;

    public FindDevicesTask(NearbyFinderManager finder, NearbyFinderVisitor nearbyFinderVisitor) {
        this.finder = finder;
        this.nearbyFinderVisitor = nearbyFinderVisitor;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {

                    @Override
                    public void run() {
                            finder.getNearbyDevicesFinders().forEach(nearbyFinder -> finder.addDevices(nearbyFinder.stopFindingAndCollectDevices()));
                            timer.cancel();
                            timer.purge();
                            runOnUiThread(() -> nearbyFinderVisitor.onNearbyDevicesSearchFinished());
                    }

                }, timeoutInMillis
        );

        finder.getNearbyDevicesFinders().forEach(AbstractNearbyDevicesFinder::startFindingDevices);
        return null;
    }

    /**
     * Sets recommended timeout for task in milliseconds
     * @param timeoutInMillis timeout in milliseconds
     * @return task
     */
    public FindDevicesTask setRecommendedTimeout(int timeoutInMillis) {
        this.timeoutInMillis = timeoutInMillis;
        return this;
    }

}
