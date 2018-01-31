package cz.cvut.fel.matyapav.nearbytest.nearbystatus.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */

public class HttpProvider {

    private static HttpProvider instance;
    private RequestQueue httpRequestQueue;
    private static Context context;

    private HttpProvider(Context context) {
        HttpProvider.context = context;
        httpRequestQueue = getRequestQueue();
    }

    public static synchronized HttpProvider getInstance(Context context) {
        if (instance == null) {
            instance = new HttpProvider(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (httpRequestQueue == null) {
            httpRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return httpRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (httpRequestQueue != null) {
            httpRequestQueue.cancelAll(tag);
        }
    }
}
