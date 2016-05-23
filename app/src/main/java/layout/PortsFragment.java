package layout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.rackdon.com.securitycam.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class PortsFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.saveBt) Button saveBt;
    @BindView(R.id.editTextServer) EditText serverET;
    private ViewGroup fragmentView;

    public PortsFragment() {
        // Required empty public constructor
    }

    public static PortsFragment newInstance() {
        PortsFragment fragment = new PortsFragment();
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

        final String serverPort = this.getActivity().getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("ServerPort", "8082");

        View rootView = inflater.inflate(R.layout.fragment_ports, container, false);
        ButterKnife.bind(this, rootView);
        saveBt.setOnClickListener(this);
        fragmentView = container;
        serverET.setText(serverPort);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBt:
                savePorts();
                break;
        }
    }

    public void savePorts() {
        this.getActivity().getSharedPreferences("SecurityCam", Context.MODE_PRIVATE).edit()
                .putString("ServerPort", serverET.getText().toString())
                .commit();
        fragmentView.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), "Ports saved succesfully", Toast.LENGTH_LONG).show();
    }
}
