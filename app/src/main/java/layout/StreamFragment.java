package layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.rackdon.com.securitycam.R;
import app.rackdon.com.securitycam.stream.DoRead;
import app.rackdon.com.securitycam.stream.MjpegInputStream;
import app.rackdon.com.securitycam.stream.MjpegView;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class StreamFragment extends Fragment {
    @InjectView( R.id.stream ) MjpegView mv;
    private boolean showFps = false;
    private Context context;

    public StreamFragment() {
        // Required empty public constructor
    }

    public static StreamFragment newInstance(Context context) {
        return newInstance(context, "Default Value");
    }
    public static StreamFragment newInstance(Context context, String value) {
        StreamFragment streamFragment = new StreamFragment();
        streamFragment.setContext(context);
        Bundle bundle = new Bundle();
        bundle.putString("key", value);
        streamFragment.setArguments(bundle);
        return streamFragment;
    }

    private void setContext (Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stream, container, false);
        ButterKnife.inject(this, rootView);

        return rootView;
    }

    public void onPause() {
        super.onPause();
        mv.stopPlayback();
    }

    public void onStop() {
        super.onStop();
        mv.stopPlayback();
        mv.setBackgroundColor(Color.BLACK);
    }

    public void start() {
        final String IP = this.getActivity().getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("Url", "");
        final String PORT = ":8081";
        final String URL = "http://" + IP + PORT;

        DoRead.DoReadCallback callback = new DoRead.DoReadCallback() {
            @Override
            public void onFinish(MjpegInputStream result) {
                Log.wtf("StreamFragment", "onFinish");
                mv.setBackgroundColor(Color.TRANSPARENT);
                mv.setSource(result);
                mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
                showFps = !showFps;
                mv.showFps( showFps );
            }

            @Override
            public void onError(String e) {
                Log.wtf("Error", e);
                AlertDialog.Builder dialog = createDialog(context);
                dialog.show();
            }
        };

        new DoRead(callback).execute(URL);
    }

    public AlertDialog.Builder createDialog(Context context) {
        return new AlertDialog.Builder(context)
                .setTitle("There is no video in the server")
                .setMessage("Please ensure you have the correct server url and the server is on")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    public Bitmap takeScreenshot() {
        return mv.takeScreenshotImage();
    }
}
