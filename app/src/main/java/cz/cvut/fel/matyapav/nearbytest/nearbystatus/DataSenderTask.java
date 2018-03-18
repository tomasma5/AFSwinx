package cz.cvut.fel.matyapav.nearbytest.nearbystatus;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.model.DeviceStatusWithNearby;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.GlobalConstants;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.util.HttpMethod;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class DataSenderTask extends AsyncTask<Void, Integer, Object> {

    private static final int TIMEOUT_IN_MILLIS = 5000;

    private String serverUrl;
    private DeviceStatusWithNearby deviceStatusWithNearby;


    public DataSenderTask(DeviceStatusWithNearby deviceStatusWithNearby, String serverUrl) {
        this.deviceStatusWithNearby = deviceStatusWithNearby;
        this.serverUrl = serverUrl;
    }

    @Override
    protected Object doInBackground(Void... voids) {
        String json = serializeDeviceStatusWithNearbyIntoJson();
        if (serverUrl == null) {
            Log.e(GlobalConstants.APPLICATION_TAG, "[JSON - ERROR] Server URL was not set!");
            return null;
        }
        if (json == null) {
            Log.e(GlobalConstants.APPLICATION_TAG, "[JSON - ERROR] Missing sended data!");
            return null;
        }

        InputStream inputStream;
        String response = null;
        HttpURLConnection urlConnection = null;
        try {
            System.out.println("URL " + serverUrl);
            System.out.println("HTTP METHOD" + HttpMethod.POST.toString());

            URL url = new URL(serverUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(TIMEOUT_IN_MILLIS);
            urlConnection.setConnectTimeout(TIMEOUT_IN_MILLIS);
            urlConnection.setRequestMethod(HttpMethod.POST.toString());
            urlConnection.setDoInput(true);

            System.err.println("DATA " + json);
            urlConnection.setDoOutput(true);
            OutputStream os = urlConnection.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            int responseCode = urlConnection.getResponseCode();
            String responseMsg = urlConnection.getResponseMessage();
            System.err.println("RESPONSE CODE " + responseCode);
            if (responseCode < 200 || responseCode >= 300) {
                throw new ConnectException(responseCode + " " + responseMsg);
            } else {
                inputStream = urlConnection.getInputStream();
            }
            if (inputStream != null) {
                response = convertInputStreamToString(inputStream);
                System.err.println("RESPONSE IS " + response);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private String serializeDeviceStatusWithNearbyIntoJson() {
        Gson gson = new Gson();
        return gson.toJson(deviceStatusWithNearby);
    }
}
