package app.rackdon.com.securitycam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import app.rackdon.com.securitycam.notification.NotificationService;
import app.rackdon.com.securitycam.screenshot.Screenshot;
import layout.StreamFragment;

public class MainActivity extends AppCompatActivity {
    private StreamFragment streamFragment;
    private AlertDialog.Builder dialog;
    private Button changeableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        streamFragment = StreamFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, streamFragment)
                .commit();

        createDialog();
        changeableButton = (Button) findViewById(R.id.changeableButton);

        Intent serverService = new Intent(this, NotificationService.class);
        startService(serverService);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isTextStart = "Start".equals(changeableButton.getText());
        if (!isTextStart) {
            streamFragment.start(this);
        }
    }

    public void startStop(View v){
        if ( isConnection() ) {
            changeButton();
        }
        else {
            dialog.show();
        }
    }

    public void Screenshot(View v){
        Bitmap picture = streamFragment.takeScreenshot();
        if (picture != null) {
            Screenshot ScreenshotClass = new Screenshot(findViewById(R.id.container), this, picture);
            ScreenshotClass.obtainScreenshot();
        }
        else {
            Toast toast = Toast.makeText (this, "There is no image to save", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.container);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            frameLayout.getLayoutParams().height = -1;
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

            frameLayout.getLayoutParams().height = 0;
        }
    }

    public boolean isConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void createDialog(){
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Unable to connect");
        dialog.setMessage("Please check your Internet connection");
        final Intent settings = new Intent(Settings.ACTION_SETTINGS);
        dialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(settings);
            }
        });

    }

    public void changeButton() {
        boolean isTextStart = "Start".equals(changeableButton.getText());

        if(isTextStart) {
            changeableButton.setText("Stop");
            streamFragment.start(this);
        }
        else {
            changeableButton.setText("Start");
            streamFragment.onStop();
        }
    }
}
