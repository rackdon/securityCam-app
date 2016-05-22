package app.rackdon.com.securitycam;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.rackdon.com.securitycam.http.GetPicture;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PicturesActivity extends AppCompatActivity {
    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.textView) TextView textView;
    Long unixDate;
    String imageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        ButterKnife.bind(this);
        unixDate = getIntent().getLongExtra("UNIX_DATE", 0);
        imageType = getIntent().getStringExtra("IMAGE_TYPE");
        imageView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        new GetPicture(this, callback).execute(unixDate.toString(), imageType);
    }

    GetPicture.GetPictureCallback callback = new GetPicture.GetPictureCallback() {
        @Override
        public void onFinish(Bitmap b) {
            imageView.setImageBitmap(b);
            imageView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onError(String errorMsg) {
            textView.setText(errorMsg);
            textView.setVisibility(View.VISIBLE);
        }
    };
}
