package app.rackdon.com.securitycam;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.rackdon.com.securitycam.pictures.Picture;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PicturesActivity extends AppCompatActivity {
    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.textView) TextView textView;
    String date;
    String imageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        ButterKnife.bind(this);
        date = getIntent().getStringExtra("DATE");
        imageType = getIntent().getStringExtra("IMAGE_TYPE");
        imageView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        new Picture(this, callback).execute(date, imageType);
    }

    Picture.GetPictureCallback callback = new Picture.GetPictureCallback() {
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
