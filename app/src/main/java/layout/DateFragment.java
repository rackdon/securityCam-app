package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import app.rackdon.com.securitycam.ImageDownloadActivity;
import app.rackdon.com.securitycam.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DateFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.datePicker) DatePicker datePicker;
    @BindView(R.id.cancelBT) Button cancelButton;
    @BindView(R.id.okBT) Button okButton;
    View fragmentView;
    ImageDownloadActivity activity;

    public DateFragment() {
        // Required empty public constructor
    }

    public static DateFragment newInstance() {
        DateFragment fragment = new DateFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_date, container, false);
        ButterKnife.bind(this, rootView);
        fragmentView = container;
        datePicker.setCalendarViewShown(false);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        activity = (ImageDownloadActivity) getActivity();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelBT:
                fragmentView.setVisibility(View.INVISIBLE);
                break;
            case R.id.okBT:
                activity.setDate(datePicker.getMonth() + 1, datePicker.getDayOfMonth(), datePicker.getYear());
                fragmentView.setVisibility(View.INVISIBLE);
                break;
        }
    }

}
