package app.rackdon.com.securitycam.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.LinearLayout;

import app.rackdon.com.securitycam.http.DeletePictures;

/**
 * Created by Rackdon on 3/4/16.
 */
public class Dialogs {
    private Context context;

    public Dialogs(Context context) {
        this.context = context;
    }

    public AlertDialog.Builder createResetDbDialog(){
        return new AlertDialog.Builder(context)
                .setTitle("Reset DeletePictures")
                .setMessage("WARNING: This changes can not be undone")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeletePictures(context).execute();
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

    public AlertDialog.Builder createUrlDialog(final CreateUrlDialogCallback callback){
        return new AlertDialog.Builder(context)
                .setTitle("Url")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.saveUrl();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    public AlertDialog.Builder createWarningDialog(final CreateWarningDialogCallback callback) {
        return new AlertDialog.Builder(context)
                .setTitle("WARNING!!!")
                .setMessage("Modify this values can cause application problems. " +
                        "Make changes under your own risk")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.showFragment();
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

    public interface  CreateWarningDialogCallback {
        void showFragment();
    }
}
