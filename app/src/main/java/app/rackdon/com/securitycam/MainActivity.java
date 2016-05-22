package app.rackdon.com.securitycam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import app.rackdon.com.securitycam.notification.NotificationService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void videoActivity(View view) {
        startActivity(new Intent(this, StreamActivity.class));
    }

    public void imageDownloadActivity(View view) {
        startActivity(new Intent(this, ImageDownloadActivity.class));
    }

    public void configurationActivity(View view) {
        startActivity(new Intent(this, ConfigurationActivity.class));
    }

    public boolean hasUrl() {
        String url = getSharedPreferences("SecurityCam", Context.MODE_PRIVATE).getString("Url", "");

        return !url.equals("") && !url.equals("http://")? true : false;
    }

    public AlertDialog.Builder createUrlDialog(final Context context) {
        return new AlertDialog.Builder(context)
                .setTitle("Url needed")
                .setMessage("Please set the Url server")
                .setPositiveButton("Configuration", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(context, ConfigurationActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!hasUrl()) {
            createUrlDialog(this).show();
        }
    }
}
