package app.rackdon.com.securitycam;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import app.rackdon.com.securitycam.dialogs.Dialogs;
import app.rackdon.com.securitycam.screenshot.Screenshot;
import app.rackdon.com.securitycam.utils.UtilsCommon;
import butterknife.BindView;
import butterknife.ButterKnife;
import layout.StreamFragment;

public class StreamActivity extends AppCompatActivity {
    private StreamFragment streamFragment;
    @BindView(R.id.changeableButton) Button changeableButton;
    @BindView(R.id.container) FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        ButterKnife.bind(this);

        streamFragment = StreamFragment.newInstance(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, streamFragment)
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isTextStart = "Start".equals(changeableButton.getText());
        if (!isTextStart) {
            streamFragment.start();
        }
    }

    public void startStop(View v){
        if ( new UtilsCommon().isConnection(this) ) {
            changeButton();
        }
        else {
            new Dialogs(this).createConnectionDialog().show();
        }
    }

    public void screenshot(View v){
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
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            frameLayout.getLayoutParams().height = -1;
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

            frameLayout.getLayoutParams().height = 0;
        }
    }

    public void changeButton() {
        if(changeableButton.getText().toString().equals(getResources().getString(R.string.start))) {
            streamFragment.start();
            changeableButton.setText(R.string.stop);
        }
        else {
            changeableButton.setText(R.string.start);
            streamFragment.onStop();
        }
    }
}
