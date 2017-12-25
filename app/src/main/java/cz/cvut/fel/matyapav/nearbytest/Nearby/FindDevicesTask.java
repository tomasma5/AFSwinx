package cz.cvut.fel.matyapav.nearbytest.Nearby;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cz.cvut.fel.matyapav.nearbytest.Nearby.Finders.INearbyDevicesFinder;
import cz.cvut.fel.matyapav.nearbytest.Nearby.Finders.SubnetDevicesFinder;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

class FindDevicesTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private List<INearbyDevicesFinder> nearbyDevicesFinders;
    private NearbyFinder finder;
    private int timeoutInMillis = 12000;

    public FindDevicesTask(Activity activity, NearbyFinder finder) {
        this.activity = activity;
        this.finder = finder;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        nearbyDevicesFinders.forEach(INearbyDevicesFinder::startFindingDevices);
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        nearbyDevicesFinders.forEach(nearbyFinder ->  finder.addDevices(nearbyFinder.stopFindingAndCollectDevices()));
                    }
                }, timeoutInMillis
        );
        return null;
    }

    FindDevicesTask addNearbyDevicesFinder(INearbyDevicesFinder finder) {
        if(finder instanceof SubnetDevicesFinder) {
            ((SubnetDevicesFinder) finder).setTimeOutMillis(timeoutInMillis);
        }
        if (nearbyDevicesFinders == null) {
            nearbyDevicesFinders = new ArrayList<>();
        }
        nearbyDevicesFinders.add(finder);
        return this;
    }

    FindDevicesTask setRecommendedTimeout(int timeoutInMillis) {
        if(nearbyDevicesFinders != null) {
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
