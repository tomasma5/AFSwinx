package cz.cvut.fel.matyapav.afandroid.rest;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.ConnectionSecurity;
import com.tomscz.afswinx.rest.connection.HttpMethod;
import com.tomscz.afswinx.rest.connection.SecurityMethod;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class RequestTask extends AsyncTask<String, Integer, Object> {

    private HttpMethod httpMethod;
    private Map<String, String> headerParameters;
    private ConnectionSecurity connectionSecurity;
    private String address;
    private Object data;

    public RequestTask(String url) {
        this.address = url;
    }

    public RequestTask(AFSwinxConnection connection) {
        this.headerParameters = connection.getHeaderParams();
        this.connectionSecurity = connection.getSecurity();
        this.address = Utils.getConnectionEndPoint(connection);
        this.httpMethod = connection.getHttpMethod();
        if (connection.getAcceptedType() != null) {
            addHeaderParameter("Content-Type", connection.getContentType().toString());
        }
    }

    public RequestTask(final Context context, HttpMethod method, Map<String, String> headerParameters,
                       ConnectionSecurity connectionSecurity, Object data, String url) {
        this.httpMethod = method;
        this.address = url;
        this.headerParameters = headerParameters;
        this.connectionSecurity = connectionSecurity;
        this.data = data;
    }

    public RequestTask setHttpMethod(HttpMethod method) {
        this.httpMethod = method;
        return this;
    }

    public RequestTask setHeaderParameters(Map<String, String> headerParameters) {
        this.headerParameters = headerParameters;
        return this;
    }

    public RequestTask addHeaderParameter(String key, String value) {
        if (headerParameters == null) {
            headerParameters = new HashMap<>();
        }
        headerParameters.put(key, value);
        return this;
    }

    public RequestTask setConnectionSecurity(ConnectionSecurity security) {
        this.connectionSecurity = connectionSecurity;
        return this;
    }

    public RequestTask setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(String... params) {
        InputStream inputStream;
        String response = null;
        HttpURLConnection urlConnection = null;
        try {
            System.out.println("URL " + address);
            System.out.println("HTTP METHOD " + httpMethod.toString().toUpperCase());

            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod(httpMethod.toString().toUpperCase());
            addHeaderParameter("Application", AFAndroid.getInstance().getProxyApplicationContext());
            if (headerParameters != null) {
                for (Map.Entry<String, String> headerParam : headerParameters.entrySet()) {
                    urlConnection.setRequestProperty(headerParam.getKey(), headerParam.getValue());
                }
            }

            urlConnection.setDoInput(true);

            if (connectionSecurity != null) {
                if (connectionSecurity.getMethod().equals(SecurityMethod.BASIC)) {
                    String encoded = Base64.encodeToString((connectionSecurity.getUserName() + ":" + connectionSecurity.getPassword()).getBytes(), Base64.NO_WRAP);
                    urlConnection.setRequestProperty("Authorization", "Basic " + encoded);
                    System.out.println("SECURITY " + "Basic " + encoded);
                }
            }

            if (data != null && (httpMethod.equals(HttpMethod.POST) || httpMethod.equals(HttpMethod.PUT))) {
                System.err.println("DATA " + data.toString());
                urlConnection.setDoOutput(true);
                OutputStream os = urlConnection.getOutputStream();
                os.write(data.toString().getBytes("UTF-8"));
                os.close();

            }

            int responseCode = urlConnection.getResponseCode();
            String responseMsg = urlConnection.getResponseMessage();
            System.err.println("RESPONSE CODE " + responseCode);
            if (responseCode < 200 || responseCode >= 300) {
                throw new ConnectException(responseCode + " " + responseMsg);
            } else {
                inputStream = urlConnection.getInputStream();
            }
            if (inputStream != null) {
                response = Utils.convertInputStreamToString(inputStream);
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

    @Override
    protected void onPostExecute(final Object response) {
        super.onPostExecute(response);
    }
}
