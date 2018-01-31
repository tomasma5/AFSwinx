package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.task;

import android.os.AsyncTask;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.NearbyFinderManager;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.finder.AbstractNearbyDevicesFinder;

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
        finder.getNearbyDevicesFinders().forEach(AbstractNearbyDevicesFinder::startFindingDevices);
        try {
            Thread.sleep(timeoutInMillis);
            finder.getNearbyDevicesFinders().forEach(nearbyFinder -> finder.addDevices(nearbyFinder.stopFindingAndCollectDevices()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        nearbyFinderVisitor.onNearbyDevicesSearchFinished();
    }
}
