package app.rackdon.com.securitycam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.rackdon.com.securitycam.utils.UtilsCommon;
import butterknife.BindView;
import butterknife.ButterKnife;
import layout.DateFragment;
import layout.TimeFragment;

public class ImageDownloadActivity extends AppCompatActivity {
    @BindView(R.id.dateBT) Button dateBT;
    @BindView(R.id.timeBT) Button timeBT;
    @BindView(R.id.dateContainer) View dateView;
    @BindView(R.id.timeContainer) View timeView;
    @BindView(R.id.imageTypesSpinner) Spinner imageDownloadTypeSpinner;
    UtilsCommon utilsCommon;
    private Calendar calendar;
    private String spinnerSelection;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download);
        ButterKnife.bind(this);
        setActualDateValues();
        addSpinnerListener();
        startFragments();
        utilsCommon = new UtilsCommon();
    }

    public void addSpinnerListener() {
        imageDownloadTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelection =
                        parent.getItemAtPosition(position).toString().split(" ")[0].toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void Download(View view) {
        String date = utilsCommon.joinDateAsString(year, month, day, hour, min, 00);
        Intent intent = new Intent(this, PicturesActivity.class);
        intent.putExtra("DATE", date);
        intent.putExtra("IMAGE_TYPE", spinnerSelection);
        startActivity(intent);
    }

    private void setActualDateValues() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        setDateInButton(month, day, year);
        setTimeInButton(hour, min);
    }

    public void setDateInButton(int month, int day, int year) {
        dateBT.setText(String.valueOf(month) + "-" + String.valueOf(day) + "-" + String.valueOf(year));
    }

    public void setTimeInButton(int hour, int min) {
        timeBT.setText(String.valueOf(hour) + ":" + String.valueOf(min));
    }

    public void startFragments() {
        timeView.setVisibility(View.INVISIBLE);
        TimeFragment timeFragment = TimeFragment.newInstance(hour, min);
        dateView.setVisibility(View.INVISIBLE);
        DateFragment dateFragment = DateFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.dateContainer, dateFragment, "dateFragment")
                .add(R.id.timeContainer, timeFragment, "timeFragment")
                .commit();
    }

    public void showHourFragment(View view) {
        timeView.setVisibility(View.VISIBLE);
    }

    public void showDateFragment(View view) {
        dateView.setVisibility(View.VISIBLE);
    }

    public void setDate(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
        setDateInButton(this.month, this.day, this.year);
    }

    public void setHour(int hour, int min) {
        this.hour = hour;
        this.min = min;
        setTimeInButton(this.hour, this.min);
    }
}
