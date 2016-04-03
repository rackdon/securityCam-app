package app.rackdon.com.securitycam.stream;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import app.rackdon.com.securitycam.httpCalls.Stream;

public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
    private DoReadCallback callback;

    public DoRead(DoReadCallback callback){
        this.callback = callback;
    }

    protected MjpegInputStream doInBackground(String... myurl) {
        InputStream inputStream = new Stream().getVideoStream(myurl[0]);
        return inputStream != null ? new MjpegInputStream(inputStream) : null;
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