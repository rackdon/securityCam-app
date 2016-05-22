package app.rackdon.com.securitycam.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rackdon on 14/5/16.
 */
public class GetPicture extends AsyncTask<String, Void, Bitmap> {

    private Context context;
    private GetPictureCallback callback;
    private String SERVERIP;
    private String PORT;
    private final String ROUTE = "/pictures/picture";
    private final String TAG = "GET /pictures/picture";
    private String errorMsg = "There is no image to display";

    public GetPicture(Context context, GetPictureCallback callback) {
        this.context = context;
        SERVERIP = context.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("Url", "");
        PORT = context.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("ServerPort", "8082");
        this.callback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            String query = makeQuery(params[0], params[1]);
            return getPicture(query);
        } catch (IOException e) {
            Log.wtf(TAG, "Request failed", e);
            errorMsg = "Connection with the server failed";
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if( bitmap != null ) {
            callback.onFinish( bitmap );
        }
        else {
            callback.onError( errorMsg );
        }
    }

    public Bitmap getPicture(String query) throws IOException{
        int responseCode;
        InputStream is = null;
        HttpURLConnection connection = null;
        Log.wtf(TAG, "1. Sending http request");
        try {
            URL url = new URL(SERVERIP + ":" + PORT + ROUTE + "?" + query);
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

    public String makeQuery(String minDate, String type) {
        String query = "";
//        query += "date={$gte:" + date + "}";
        query += "minDate=" + minDate;
        query += type.equals("all") ? "" : "&type=" + type;
        return  query;
    }

    public interface GetPictureCallback {
        public void onFinish(Bitmap b);
        public void onError(String errorMsg);
    }
}
