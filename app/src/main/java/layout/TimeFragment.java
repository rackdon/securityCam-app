package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import app.rackdon.com.securitycam.ImageDownloadActivity;
import app.rackdon.com.securitycam.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.timePicker) TimePicker timePicker;
    @BindView(R.id.cancelBT) Button cancelButton;
    @BindView(R.id.okBT) Button okButton;
    private View fragmentView;
    private ImageDownloadActivity activity;
    private int hour;
    private int min;


    public TimeFragment() {
        // Required empty public constructor
    }

    public static TimeFragment newInstance(int currentHour, int currentMin) {
        TimeFragment fragment = new TimeFragment();
        Bundle args = new Bundle();
        args.putInt("hour", currentHour);
        args.putInt("min", currentMin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hour = getArguments().getInt("hour");
        min = getArguments().getInt("min");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hour, container, false);
        fragmentView = container;
        ButterKnife.bind(this, rootView);
        activity = (ImageDownloadActivity) getActivity();
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(hour);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        addHourPickerListener();
        return rootView;
    }

    public void addHourPickerListener() {
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelBT:
                fragmentView.setVisibility(View.INVISIBLE);
                break;
            case R.id.okBT:
                activity.setHour(hour, min);
                fragmentView.setVisibility(View.INVISIBLE);
                break;
        }
    }

}
