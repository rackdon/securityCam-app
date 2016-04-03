package app.rackdon.com.securitycam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import app.rackdon.com.securitycam.notification.NotificationService;

public class ConfigurationActivity extends AppCompatActivity {
    EditText inputUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
    }

    public void setUrl(View view) {
        AlertDialog.Builder dialog = createDialog();
        addEditTextToDialog(dialog);
        dialog.show();
    }

    public AlertDialog.Builder createDialog(){
        return new AlertDialog.Builder(this)
                .setTitle("Url")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveUrl(inputUrl.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    public void addEditTextToDialog (AlertDialog.Builder dialog) {
        inputUrl = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        inputUrl.setLayoutParams(lp);
        inputUrl.setHint(getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("Url", ""));
        dialog.setView(inputUrl);
    }

    public void saveUrl(String url) {
        url = containsHTTP(url) ? url : "http://" + url;

        getSharedPreferences("SecurityCam", Context.MODE_PRIVATE).edit()
                .putString("Url", url)
                .commit();
        startService();
    }

    public boolean containsHTTP(String url) {
        return url.startsWith("http://");
    }

    public void startService() {
        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("url", getSharedPreferences("SecurityCam", Context.MODE_PRIVATE).getString("Url", ""));
        startService(intent);
    }

    // FOR STOP THE NOTIFICATION SERVICE
    // stopService(new Intent(this, NotificationService.class)
}
