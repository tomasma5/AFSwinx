package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
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

    NearbyStatusFacade(Context context, NearbyFinderManager nearbyFinderManager, DeviceStatusManager deviceStatusManager) {
        this.context = context;
        this.nearbyFinderManager = nearbyFinderManager;
        this.deviceStatusManager = deviceStatusManager;
        this.sendAsJsonToServer = false;
    }

    /**
     * Runs device status mining and nearby devices finding processes
     */
    public void runProcess() {
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
            String json = serializeDeviceStatusWithNearbyIntoJson();
            sendDataToServer(json);
        }
    }

    /**
     * Gets found data as @{@link DeviceStatusWithNearby} wrapper
     * @return found data
     */
    public DeviceStatusWithNearby getFoundData() {
        return deviceStatusWithNearby;
    }

    private void sendDataToServer(String json) {
        if(serverUrl == null) {
            Log.e(GlobalConstants.APPLICATION_TAG, "[JSON - ERROR] Server URL was not set!");
            return;
        }
        if(json == null) {
            Log.e(GlobalConstants.APPLICATION_TAG, "[JSON - ERROR] Missing sended data!");
            return;
        }
        // Instantiate the RequestQueue.
        HttpProvider http = HttpProvider.getInstance(context);
        String path = "/api/consumer/add";
        StringRequest jsonRequest = new StringRequest(POST, serverUrl + path,
                response -> Log.i(GlobalConstants.APPLICATION_TAG, "[JSON - SUCCESS] Sending JSON data was successfull."),
                error -> Log.e(GlobalConstants.APPLICATION_TAG, "[JSON - ERROR] Sending JSON data failed. Message: " + error.getMessage())
        ) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return json.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", json, "utf-8");
                    return null;
                }
            }
        };
        http.addToRequestQueue(jsonRequest, "DeviceStatusWithNearbyJson");
    }

    private String serializeDeviceStatusWithNearbyIntoJson(){
        Gson gson = new Gson();
        return gson.toJson(deviceStatusWithNearby);
    }
}
