package com.aleperf.pathfinder.copernicana.iss;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.IssPosition;
import com.aleperf.pathfinder.copernicana.utilities.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IssPositionFragment extends Fragment {

    private final String ISS_LATITUDE = "iss latitude";
    private final String ISS_LONGITUDE = "iss longitude";
    private final String DATE = "date last check";
    private final String SHOULD_CHECK = "should check";

    private Unbinder unbinder;
    @Inject
    ViewModelProvider.Factory factory;
    @BindView(R.id.iss_position_check)
    TextView issDateCheckedTextView;
    @BindView(R.id.iss_pos_latitude_value)
    TextView latitudeTextView;
    @BindView(R.id.iss_pos_longitude_value)
    TextView longitudeTextView;
    @BindView(R.id.iss_pos_button_check)
    Button buttonCheckPosition;
    private IssPositionViewModel issPositionViewModel;
    private MutableLiveData<IssPosition> issPositionMLD;
    private String latitude;
    private String longitude;
    private String date;
    private boolean shouldCheck = true;


    //default empty constructor
    public IssPositionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) this.getActivity().getApplication())
                .getCopernicanaApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_iss_pos, container, false);
        unbinder =  ButterKnife.bind(this, rootView);
        if (savedInstanceState != null) {
            String noValue = getString(R.string.iss_pos_no_value);
            latitudeTextView.setText(savedInstanceState.getString(ISS_LATITUDE, noValue));
            longitudeTextView.setText(savedInstanceState.getString(ISS_LONGITUDE, noValue));
            String date = savedInstanceState.getString(DATE);
            if (date != null) {
                issDateCheckedTextView.setText(String.format(getString(R.string.iss_position_date_check),
                        date));
            } else {
                issDateCheckedTextView.setText(getString(R.string.iss_position_error));
            }
            shouldCheck = savedInstanceState.getBoolean(SHOULD_CHECK);
        }
        buttonCheckPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (issPositionViewModel != null) {
                    issPositionViewModel.checkIssPositionNow();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        issPositionViewModel = ViewModelProviders.of(this, factory).get(IssPositionViewModel.class);
        issPositionMLD = issPositionViewModel.getIssPositionMLD();
        subscribe();
        if (shouldCheck) {
            issPositionViewModel.checkIssPositionNow();
        }
    }

    private void subscribe() {
        Observer<IssPosition> positionObserver = new Observer<IssPosition>() {
            @Override
            public void onChanged(@Nullable IssPosition issPosition) {
                if (issPosition != null) {
                    shouldCheck = false;
                    if (!issPosition.getMessage().equals(IssPosition.NO_CONNECTION)) {
                        double lat = issPosition.getIssPosition().getLatitude();
                        double lon = issPosition.getIssPosition().getLongitude();
                        latitude = String.valueOf(lat);
                        longitude = String.valueOf(lon);
                        latitudeTextView.setText(latitude);
                        longitudeTextView.setText(longitude);
                        long timestamp = issPosition.getTimestamp();
                        date = Utils.getLocalTimeFromUnixTimestamp(timestamp);
                        String dateText = String.format(getString(R.string.iss_position_date_check),
                                date);
                        issDateCheckedTextView.setText(dateText);
                    } else {
                        latitude = getString(R.string.iss_pos_no_value);
                        longitude = getString(R.string.iss_pos_no_value);
                        date = getString(R.string.iss_position_error);
                        issDateCheckedTextView.setText(date);
                    }
                }
            }
        };
        issPositionMLD.observe(this, positionObserver);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ISS_LATITUDE, latitude);
        outState.putString(ISS_LONGITUDE, longitude);
        outState.putString(DATE, date);
        outState.putBoolean(SHOULD_CHECK, shouldCheck);
    }
}
