package app.rackdon.com.securitycam.stream;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
    private static final String TAG = "MjpegActivity";
    private DoReadCallback callback;
    private Context context;
    private AlertDialog.Builder dialog;

    public DoRead(DoReadCallback callback, Context context){
        this.callback = callback;
        this.context = context;
    }

    protected MjpegInputStream doInBackground(String... myurl) {
        //TODO: if camera has authentication deal with it and don't just not work
        InputStream inputStream = null;

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
            createDialog();
//            dialog.show();
            return null;
        }

//        return null;
    }

    protected void onPostExecute(MjpegInputStream result) {
        if (result != null)
            callback.onFinish(result);
        else
            callback.onError( "NULL Mjpeg Input Stream" );

    }

    public void createDialog(){
        dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Error connecting to camera");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    public interface DoReadCallback{
        public void onFinish(MjpegInputStream result);
        public void onError(String errorMsg);
    }
}