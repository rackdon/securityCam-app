package app.rackdon.com.securitycam.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rackdon on 3/4/16.
 */
public class DeletePictures extends AsyncTask<String, Void, Integer>{
    private Context context;
    private final String TAG = "DELETE /pictures";
    private String SERVERIP;
    private String PORT;
    private final String ROUTE = "/pictures";

    public DeletePictures(Context context) {
        this.context = context;
        SERVERIP = context.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("Url", "");
        PORT = context.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("ServerPort", "8082");
    }

    @Override
    protected Integer doInBackground(String... params) {
        return resetDatabase();
    }

    @Override
    protected void onPostExecute(Integer status) {
        Toast toast;
        if(status == 200) {
            toast = Toast.makeText(context, "Database has been reset correctly", Toast.LENGTH_LONG);
        } else {
            toast = Toast.makeText(context, "Reset database has failed", Toast.LENGTH_LONG);
        }
        toast.show();
    }

    public int resetDatabase() {
        int responseCode;
        HttpURLConnection connection = null;
        Log.wtf(TAG, "1. Sending http request");
        try {
            URL url = new URL(SERVERIP + ":" + PORT + ROUTE);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.connect();
            responseCode = connection.getResponseCode();
            Log.i("Response", "The response code is: " + responseCode);
        } catch (Exception e) {
            responseCode = 500;
            Log.wtf(TAG, "Request failed", e);
        } finally {
            connection.disconnect();
        }
        return responseCode;
    }
}
