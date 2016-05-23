package app.rackdon.com.securitycam.pictures;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import app.rackdon.com.securitycam.http.GetStream;

/**
 * Created by Rackdon on 14/5/16.
 */
public class Picture extends AsyncTask<String, Void, Bitmap> {

    private Context context;
    private GetPictureCallback callback;
    private String SERVERIP;
    private String PORT;
    private final String ROUTE = "/pictures/picture";
    private final String TAG = "GET /pictures/picture";
    private String errorMsg = "There is no image to display";

    public Picture(Context context, GetPictureCallback callback) {
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
            String url = SERVERIP + ":" + PORT + ROUTE + query;
            return new GetStream().getPictureStream(url);
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

    private String makeQuery(String minDate, String type) {
        String query = "?";
        query += "minDate=" + minDate;
        query += type.equals("all") ? "" : "&type=" + type;
        return  query;
    }

    public interface GetPictureCallback {
        void onFinish(Bitmap b);
        void onError(String errorMsg);
    }
}
