package cz.cvut.fel.matyapav.nearbytest.nearby.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cz.cvut.fel.matyapav.nearbytest.nearby.NearbyFinderManager;
import cz.cvut.fel.matyapav.nearbytest.R;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.AbstractNearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.nearby.finder.SubnetDevicesFinder;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class FindDevicesTask extends AsyncTask<Void, Integer, Void> {

    private List<AbstractNearbyDevicesFinder> nearbyDevicesFinders;
    private NearbyFinderManager finder;
    private int timeoutInMillis = 12000;

    private ProgressBar progressBar;

    public FindDevicesTask(Activity activity, NearbyFinderManager finder) {
        this.finder = finder;
        this.progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        nearbyDevicesFinders.forEach(AbstractNearbyDevicesFinder::startFindingDevices);
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
                            nearbyDevicesFinders.forEach(nearbyFinder -> finder.addDevices(nearbyFinder.stopFindingAndCollectDevices()));
                            timer.cancel();
                            timer.purge();
                        }
                        publishProgress((int) ((1.0 * i / seconds) * 100));
                    }
                }, 0, timeStep
        );
        return null;
    }

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

    public FindDevicesTask addNearbyDevicesFinder(AbstractNearbyDevicesFinder finder) {
        if (finder instanceof SubnetDevicesFinder) {
            ((SubnetDevicesFinder) finder).setTimeOutMillis(timeoutInMillis);
        }
        if (nearbyDevicesFinders == null) {
            nearbyDevicesFinders = new ArrayList<>();
        }
        nearbyDevicesFinders.add(finder);
        return this;
    }

    public FindDevicesTask setRecommendedTimeout(int timeoutInMillis) {
        if (nearbyDevicesFinders != null) {
            for (AbstractNearbyDevicesFinder finder : nearbyDevicesFinders) {
                if (finder instanceof SubnetDevicesFinder) {
                    ((SubnetDevicesFinder) finder).setTimeOutMillis(timeoutInMillis);
                }
            }
        }
        this.timeoutInMillis = timeoutInMillis;
        return this;
    }


}
