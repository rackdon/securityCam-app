package app.rackdon.com.securitycam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import app.rackdon.com.securitycam.dialogs.Dialogs;
import app.rackdon.com.securitycam.utils.UtilsCommon;

public class MainActivity extends AppCompatActivity {
    private UtilsCommon utilsCommon;
    private Dialogs dialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utilsCommon = new UtilsCommon();
        dialogs = new Dialogs(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        if(!utilsCommon.hasUrl(this)) {
            dialogs.createUrlNeededDialog(new Dialogs.CreateUrlNeededDialogCallback(){

                @Override
                public void openConfigurationActivity() {
                    startActivity(new Intent(getApplicationContext(), ConfigurationActivity.class));
                }
            }).show();
        }
    }
}
