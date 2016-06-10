package app.rackdon.com.securitycam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import app.rackdon.com.securitycam.dialogs.Dialogs;
import app.rackdon.com.securitycam.notification.NotificationService;
import app.rackdon.com.securitycam.utils.UtilsCommon;
import butterknife.BindView;
import butterknife.ButterKnife;
import layout.PortsFragment;

public class ConfigurationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.containerPortsFragment)
    View portsView;
    @BindView(R.id.notificationsSwitch)
    Switch notificationsSwitch;
    private EditText inputUrl;
    private Dialogs dialogs;
    private UtilsCommon utils;
    private Intent notificationsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        ButterKnife.bind(this);

        dialogs = new Dialogs(this);
        utils = new UtilsCommon();
        notificationsIntent = getNotificationsIntent();
        portsView.setVisibility(View.INVISIBLE);
        PortsFragment portsFragment = PortsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerPortsFragment, portsFragment, "portsFragment")
                .commit();
        notificationsSwitch.setOnCheckedChangeListener(this);
        notificationsSwitch.setChecked(getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getBoolean("Notifications", false));
    }

    public void setUrl(View view) {
        Dialogs.CreateUrlDialogCallback callback = new Dialogs.CreateUrlDialogCallback() {
            @Override
            public void saveUrl() {
                Toast toast;
                String url = inputUrl.getText().toString();
                url = utils.containsHTTP(url) ? url : "http://" + url;
                if (url.equals("http://")) {
                    Toast.makeText(
                            getApplicationContext(), "Url can not be empty", Toast.LENGTH_LONG).show();

                } else {
                    getSharedPreferences("SecurityCam", Context.MODE_PRIVATE).edit()
                            .putString("Url", url)
                            .commit();
                    if (getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                            .getBoolean("Notifications", false)) {
                        stopService();
                        notificationsIntent = getNotificationsIntent();
                        startService();
                        Toast.makeText(
                                getApplicationContext(), "Url has been changed correctly", Toast.LENGTH_LONG).show();
                    }

                }
            }
        };

        AlertDialog.Builder dialog = dialogs.createUrlDialog(callback);
        inputUrl = dialogs.addEditTextToDialog(dialog, inputUrl);
        dialog.show();
    }

    public Intent getNotificationsIntent() {
        return new Intent(this, NotificationService.class);
    }

    public void startService() {
        startService(notificationsIntent);
    }

    public void stopService() {
        stopService(notificationsIntent);
    }

    public void resetDB(View view) {
        if (!utils.isConnection(this)) {
            dialogs.createConnectionDialog().show();
        } else if (!utils.hasUrl(this)) {
            dialogs.createUrlNeededDialog().show();
        }
        else {
            dialogs.createResetDbDialog().show();
        }
    }

    public void showPortsFragment(View view) {
        if (portsView.getVisibility() == View.INVISIBLE) {
            Dialogs.CreateWarningDialogCallback callback = new Dialogs.CreateWarningDialogCallback() {
                @Override
                public void showFragment() {
                    portsView.setVisibility(View.VISIBLE);
                }
            };
            dialogs.createWarningDialog(callback).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            getSharedPreferences("SecurityCam", Context.MODE_PRIVATE).edit()
                    .putBoolean("Notifications", true)
                    .commit();
            startService();

        } else {
            getSharedPreferences("SecurityCam", Context.MODE_PRIVATE).edit()
                    .putBoolean("Notifications", false)
                    .commit();
            stopService();
        }

    }

}
