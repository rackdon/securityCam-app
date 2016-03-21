package app.rackdon.com.securitycam.stream;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
    private static final String TAG = "MjpegActivity";
    private DoReadCallback callback;

    public DoRead(DoReadCallback callback){
        this.callback = callback;
    }

    protected MjpegInputStream doInBackground(String... myurl) {
        //TODO: if camera has authentication deal with it and don't just not work
        InputStream inputStream;

        try {
            Log.wtf(TAG, "1. Sending http request");
            URL url = new URL(myurl[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            inputStream = connection.getInputStream();

            return new MjpegInputStream(inputStream);
        } catch (Exception e) {
            Log.wtf(TAG, "Request failed", e);
        }

        return null;
    }

    protected void onPostExecute(MjpegInputStream result) {
        if (result != null)
            callback.onFinish(result);
        else
            callback.onError( "NULL Mjpeg Input Stream" );
    }

    public interface DoReadCallback{
        void onFinish(MjpegInputStream result);
        void onError(String errorMsg);
    }
}