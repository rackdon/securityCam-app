package app.rackdon.com.securitycam.httpCalls;

import android.content.Context;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rackdon on 3/4/16.
 */
public class Database {
    private final String TAG = "Database request";
    private String SERVERIP;
    private String PORT;

    public Database(Context context) {
        SERVERIP = context.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("Url", "");
        PORT = context.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("ServerPort", "8082");
    }

    public void resetDatabase() {
        HttpURLConnection connection = null;
        Log.wtf(TAG, "1. Sending http request");
        try {
            URL url = new URL(SERVERIP + ":" + PORT + "/resetDatabase");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
        } catch (Exception e) {
            Log.wtf(TAG, "Request failed", e);
        } finally {
            connection.disconnect();
        }

    }
}
