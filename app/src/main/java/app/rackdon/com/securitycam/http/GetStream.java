package app.rackdon.com.securitycam.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rackdon on 3/4/16.
 */
public class GetStream {
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

    public Bitmap getPictureStream(String Url) throws IOException{
        int responseCode;
        InputStream is = null;
        HttpURLConnection connection = null;
        Log.wtf(TAG, "1. Sending http request");
        try {
            URL url = new URL(Url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            responseCode = connection.getResponseCode();
            Log.i("Response", "The response code is: " + responseCode);
            is = connection.getInputStream();
            return BitmapFactory.decodeStream(is);
        } finally {
            if(is != null) {
                is.close();
            }
            connection.disconnect();
        }
    }
}
