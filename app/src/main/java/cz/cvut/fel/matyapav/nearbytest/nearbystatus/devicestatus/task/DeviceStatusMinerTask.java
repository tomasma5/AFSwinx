package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import cz.cvut.fel.matyapav.nearbytest.R;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.DeviceStatusManager;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class DeviceStatusMinerTask extends AsyncTask<Void, Integer, Void> {

    private DeviceStatusManager deviceStatusManager;
    private DeviceStatusVisitor deviceStatusVisitor;

    private ProgressBar progressBar;

    public DeviceStatusMinerTask(Activity activity, DeviceStatusManager deviceStatusMiner, DeviceStatusVisitor deviceStatusVisitor) {
        this.deviceStatusManager = deviceStatusMiner;
        this.progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        this.deviceStatusVisitor = deviceStatusVisitor;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DeviceStatus status = new DeviceStatus();
        deviceStatusManager.getMinerList().forEach(miner -> miner.mineAndFillStatus(status));
        deviceStatusManager.setDeviceStatus(status);
        deviceStatusVisitor.onDeviceStatusMined();
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

}
