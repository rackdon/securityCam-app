package app.rackdon.com.securitycam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import app.rackdon.com.securitycam.dialogs.Dialogs;
import app.rackdon.com.securitycam.notification.NotificationService;
import app.rackdon.com.securitycam.utils.UtilsCommon;

public class ConfigurationActivity extends AppCompatActivity {
    private EditText inputUrl;
    private Dialogs dialogs;
    private UtilsCommon utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        dialogs = new Dialogs(this);
        utils = new UtilsCommon();
    }

    public void setUrl(View view) {
        Dialogs.CreateUrlDialogCallback callback = new Dialogs.CreateUrlDialogCallback() {
            @Override
            public void saveUrl() {
                String url = inputUrl.getText().toString();
                url = utils.containsHTTP(url) ? url : "http://" + url;

                getSharedPreferences("SecurityCam", Context.MODE_PRIVATE).edit()
                        .putString("Url", url)
                        .commit();
                startService();
            }
        };

        AlertDialog.Builder dialog = dialogs.createUrlDialog(callback);
        inputUrl = dialogs.addEditTextToDialog(dialog, inputUrl);
        dialog.show();
    }

    public void startService() {
        startService(new Intent(this, NotificationService.class));
    }

    public void resetDB(View view) {
        if (new UtilsCommon().isConnection(this)) {
            dialogs.createResetDbDialog().show();
        } else {
            dialogs.createConnectionDialog().show();
        }
    }

    // FOR STOP THE NOTIFICATION SERVICE
    // stopService(new Intent(this, NotificationService.class)
}
