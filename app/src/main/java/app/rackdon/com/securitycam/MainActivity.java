package app.rackdon.com.securitycam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void videoActivity(View view) {
        if (hasUrl()) {
            startActivity(new Intent(this, StreamActivity.class));
        }
        else {
            createUrlDialog(this).show();
        }
    }

    public void configurationActivity(View view) {
        startActivity(new Intent(this, ConfigurationActivity.class));
    }

    public boolean hasUrl() {
        String url = getSharedPreferences("SecurityCam", Context.MODE_PRIVATE).getString("Url", "");
        return !url.equals("") ? true : false;
    }

    public AlertDialog.Builder createUrlDialog(final Context context) {
        return new AlertDialog.Builder(context)
                .setTitle("Url needed")
                .setMessage("Please set the Url")
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
}
