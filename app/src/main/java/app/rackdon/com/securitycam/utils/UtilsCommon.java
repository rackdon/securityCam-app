package app.rackdon.com.securitycam.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Rackdon on 3/4/16.
 */
public class UtilsCommon {

    public boolean isConnection(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public boolean containsHTTP(String url) {
        return url.startsWith("http://");
    }
}
