package app.rackdon.com.securitycam.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public long getUnixTime(int year, int month, int day, int hour, int min) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(
                    String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day) +
                            " " + String.valueOf(hour) + ":" + String.valueOf(min));
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String joinDateAsString(int ... args) {
        String result = "";
        for (int i = 0; i < args.length; i++) {
            result += args[i] < 10 ? "0" + String.valueOf(args[i]) : String.valueOf((args[i]));
        }
        return result;
    }
}
