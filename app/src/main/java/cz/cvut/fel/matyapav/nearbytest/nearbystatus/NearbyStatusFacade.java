package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.task.DeviceStatusVisitor;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.model.DeviceStatusWithNearby;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.nearby.task.NearbyFinderVisitor;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.HttpProvider;

import static com.android.volley.Request.Method.POST;

/**
 * Facade for executing nearby devices finding and status mining processes
 *  TODO add possibility to periodically run the service
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyStatusFacade implements NearbyFinderVisitor, DeviceStatusVisitor{

    private Context context;
    private NearbyFinderManager nearbyFinderManager;
    private DeviceStatusManager deviceStatusManager;
    private DeviceStatusWithNearby deviceStatusWithNearby;
    private String serverUrl;
    private boolean sendAsJsonToServer;
    private DeviceStatusAndNearbySearchEvent deviceStatusAndNearbySearchEvent;

    NearbyStatusFacade(Context context, NearbyFinderManager nearbyFinderManager, DeviceStatusManager deviceStatusManager, DeviceStatusAndNearbySearchEvent nearbyDevicesSearchEvent) {
        this.context = context;
        this.nearbyFinderManager = nearbyFinderManager;
        this.deviceStatusManager = deviceStatusManager;
        this.sendAsJsonToServer = false;
        this.deviceStatusAndNearbySearchEvent = nearbyDevicesSearchEvent;
    }

    /**
     * Runs device status mining and nearby devices finding processes
     */
    public void runProcess() {
        if(deviceStatusAndNearbySearchEvent != null) {
            deviceStatusAndNearbySearchEvent.onSearchStart();
        }
        if(deviceStatusWithNearby == null){
            deviceStatusWithNearby = new DeviceStatusWithNearby();
            //set timestamp to the beginning of process
            deviceStatusWithNearby.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        }
        deviceStatusManager.mineDeviceStatus(this);
    }

    public NearbyStatusFacade sendDataToServerAfterTimeout(String serverUrl) {
        this.serverUrl = serverUrl;
        this.sendAsJsonToServer = true;
        return this;
    }

    @Override
    public void onDeviceStatusMined(){
        DeviceStatus deviceStatus = deviceStatusManager.getDeviceStatus();
        deviceStatusWithNearby.setDeviceStatus(deviceStatus);
        nearbyFinderManager.filterNearbyFindersByDeviceStatus(deviceStatus);
        nearbyFinderManager.findNearbyDevices(this);
    }

    @Override
    public void onNearbyDevicesSearchFinished() {
        deviceStatusWithNearby.setNearbyDevices(nearbyFinderManager.getFoundDevices());
        if(sendAsJsonToServer) {
            new DataSenderTask(context, deviceStatusWithNearby, serverUrl).execute();
        }
        if(deviceStatusAndNearbySearchEvent != null){
            deviceStatusAndNearbySearchEvent.onSearchFinished();
        }
    }

    /**
     * Gets found data as @{@link DeviceStatusWithNearby} wrapper
     * @return found data
     */
    public DeviceStatusWithNearby getFoundData() {
        return deviceStatusWithNearby;
    }


}
