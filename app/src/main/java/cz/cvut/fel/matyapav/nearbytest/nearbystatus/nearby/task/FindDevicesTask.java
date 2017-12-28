package cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import cz.cvut.fel.matyapav.nearbytest.R;
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

    private ProgressBar progressBar;

    public FindDevicesTask(Activity activity, NearbyFinderManager finder, NearbyFinderVisitor nearbyFinderVisitor) {
        this.finder = finder;
        this.progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        this.nearbyFinderVisitor = nearbyFinderVisitor;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        finder.getNearbyDevicesFinders().forEach(AbstractNearbyDevicesFinder::startFindingDevices);
        Timer timer = new Timer();
        int timeStep = 100;
        timer.schedule(
                new TimerTask() {
                    int i = 0;
                    int seconds = timeoutInMillis / timeStep;

                    @Override
                    public void run() {
                        i++;
                        if ((i % seconds == 0)) {
                            finder.getNearbyDevicesFinders().forEach(nearbyFinder -> finder.addDevices(nearbyFinder.stopFindingAndCollectDevices()));
                            nearbyFinderVisitor.onNearbyDevicesSearchFinished();
                            timer.cancel();
                            timer.purge();
                        }
                        publishProgress((int) ((1.0 * i / seconds) * 100));
                    }
                }, 0, timeStep
        );
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if(progressBar.getVisibility() != View.VISIBLE){
            progressBar.setVisibility(View.VISIBLE);
        }
        int currentProgress = progress[0];

        progressBar.setProgress(currentProgress);
        if(currentProgress == 100){
            progressBar.setVisibility(View.GONE);
        }
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
