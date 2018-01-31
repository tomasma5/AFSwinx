package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.model.DeviceStatusWithNearby;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.HttpProvider;

import static com.android.volley.Request.Method.POST;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class DataSenderTask extends AsyncTask<Void, Void, Void> {

    private String serverUrl;
    private DeviceStatusWithNearby deviceStatusWithNearby;
    private HttpProvider http;


    public DataSenderTask(Context context, DeviceStatusWithNearby deviceStatusWithNearby, String serverUrl) {
        this.deviceStatusWithNearby = deviceStatusWithNearby;
        this.serverUrl = serverUrl;
        this.http = HttpProvider.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String json = serializeDeviceStatusWithNearbyIntoJson();
        if(serverUrl == null) {
            Log.e(GlobalConstants.APPLICATION_TAG, "[JSON - ERROR] Server URL was not set!");
            return null;
        }
        if(json == null) {
            Log.e(GlobalConstants.APPLICATION_TAG, "[JSON - ERROR] Missing sended data!");
            return null;
        }
        // Instantiate the RequestQueue.
        StringRequest jsonRequest = new StringRequest(POST, serverUrl,
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
        //this prevents sending request twice
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        http.addToRequestQueue(jsonRequest, GlobalConstants.VOLLEY_DSWN_REQUEST_TAG);
        return null;
    }

    private String serializeDeviceStatusWithNearbyIntoJson(){
        Gson gson = new Gson();
        return gson.toJson(deviceStatusWithNearby);
    }
}
