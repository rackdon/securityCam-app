package app.rackdon.com.securitycam.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.LinearLayout;

import app.rackdon.com.securitycam.httpCalls.Database;

/**
 * Created by Rackdon on 3/4/16.
 */
public class Dialogs {
    private Context context;
    private CreateUrlDialogCallback createUrlDialogCallback;

    public Dialogs(Context context) {
        this.context = context;
    }

    public AlertDialog.Builder createResetDbDialog(){
        return new AlertDialog.Builder(context)
                .setTitle("Reset Database")
                .setMessage("WARNING: This changes can not be undone")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Database(context).resetDatabase();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    public AlertDialog.Builder createConnectionDialog(){
        return new AlertDialog.Builder(context)
                .setTitle("Unable to connect")
                .setMessage("Please check your Internet connection")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                });
    }

    public AlertDialog.Builder createUrlDialog(CreateUrlDialogCallback callback){
        createUrlDialogCallback = callback;
        return new AlertDialog.Builder(context)
                .setTitle("Url")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createUrlDialogCallback.saveUrl();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    public EditText addEditTextToDialog (AlertDialog.Builder dialog, EditText inputUrl) {
        inputUrl = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        inputUrl.setLayoutParams(lp);
        inputUrl.setHint(context.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("Url", ""));
        dialog.setView(inputUrl);

        return  inputUrl;
    }

    public interface CreateUrlDialogCallback {
        void saveUrl();
    }
}
