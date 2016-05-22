package app.rackdon.com.securitycam.stream;

import android.os.AsyncTask;

import java.io.InputStream;

import app.rackdon.com.securitycam.http.GetStream;

public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
    private DoReadCallback callback;

    public DoRead(DoReadCallback callback){
        this.callback = callback;
    }

    protected MjpegInputStream doInBackground(String... myurl) {
        InputStream inputStream = new GetStream().getVideoStream(myurl[0]);
        return inputStream != null ? new MjpegInputStream(inputStream) : null;
    }

    protected void onPostExecute(MjpegInputStream result) {
        if (result != null)
            callback.onFinish(result);
        else
            callback.onError( "NULL Mjpeg Input GetStream" );
    }

    public interface DoReadCallback{
        void onFinish(MjpegInputStream result);
        void onError(String errorMsg);
    }
}