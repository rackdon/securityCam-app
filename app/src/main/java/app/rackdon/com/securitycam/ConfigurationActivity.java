package app.rackdon.com.securitycam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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
        dialog.setView(inputUrl);
    }

    public void saveUrl(String url) {
        getSharedPreferences("SecurityCam", Context.MODE_PRIVATE).edit()
                .putString("Url", url)
                .commit();
    }
}
