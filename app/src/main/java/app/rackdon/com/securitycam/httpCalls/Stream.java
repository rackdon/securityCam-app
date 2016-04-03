package app.rackdon.com.securitycam.httpCalls;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rackdon on 3/4/16.
 */
public class Stream {
    private final String TAG = "MjpegActivity";

    public InputStream getVideoStream(String Url) {
        Log.wtf(TAG, "1. Sending http request");
        URL url = null;
        try {
            url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return connection.getInputStream();
        } catch (Exception e) {
            Log.wtf(TAG, "Request failed", e);
        }
        return null;
    }
}
