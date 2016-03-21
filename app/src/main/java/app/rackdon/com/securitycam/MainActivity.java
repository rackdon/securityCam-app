package app.rackdon.com.securitycam;

import android.content.Intent;
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
        //TODO check if the url have been stablished
        Intent intent = new Intent(this, StreamActivity.class);
        startActivity(intent);
    }

    public void configurationActivity(View view) {
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivity(intent);
    }
}
