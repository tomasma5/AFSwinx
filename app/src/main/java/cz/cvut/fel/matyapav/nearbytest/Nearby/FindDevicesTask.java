package cz.cvut.fel.matyapav.nearbytest.Nearby;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cz.cvut.fel.matyapav.nearbytest.Nearby.Finders.INearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Finders.SubnetDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.R;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

class FindDevicesTask extends AsyncTask<Void, Integer, Void> {

    private Activity activity;
    private List<INearbyDevicesFinder> nearbyDevicesFinders;
    private NearbyFinder finder;
    private int timeoutInMillis = 12000;

    private ProgressBar progressBar;

    public FindDevicesTask(Activity activity, NearbyFinder finder) {
        this.activity = activity;
        this.finder = finder;
        this.progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        nearbyDevicesFinders.forEach(INearbyDevicesFinder::startFindingDevices);
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

    FindDevicesTask addNearbyDevicesFinder(INearbyDevicesFinder finder) {
        if (finder instanceof SubnetDevicesFinder) {
            ((SubnetDevicesFinder) finder).setTimeOutMillis(timeoutInMillis);
        }
        if (nearbyDevicesFinders == null) {
            nearbyDevicesFinders = new ArrayList<>();
        }
        nearbyDevicesFinders.add(finder);
        return this;
    }

    FindDevicesTask setRecommendedTimeout(int timeoutInMillis) {
        if (nearbyDevicesFinders != null) {
            for (INearbyDevicesFinder finder : nearbyDevicesFinders) {
                if (finder instanceof SubnetDevicesFinder) {
                    ((SubnetDevicesFinder) finder).setTimeOutMillis(timeoutInMillis);
                }
            }
        }
        this.timeoutInMillis = timeoutInMillis;
        return this;
    }


}
