package com.aleperf.pathfinder.copernicana.iss;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.IssPassage;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.Unbinder;


public class IssPassageFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.edit_text_latitude)
    EditText latitudeEditText;
    @BindView(R.id.edit_text_longitude)
    EditText longitudeEditText;
    @BindView(R.id.button_calculate)
    Button buttonCalc;
    @BindView(R.id.result_text_message)
    TextView resultTextMessage;
    @BindView(R.id.iss_passage_recycler_view)
    RecyclerView recyclerView;
    @Inject
    ViewModelProvider.Factory factory;
    private MutableLiveData<List<IssPassage>> issPassagesMLD;
    private IssPassageViewModel viewModel;
    private IssPassageAdapter issPassageAdapter;
    double latitude;
    double longitude;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) getActivity().getApplication()).getCopernicanaApplicationComponent().inject(this);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_iss_passage, container, false);
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = latitudeEditText.getText().toString();
                String longitude =latitudeEditText.getText().toString();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        issPassageAdapter = new IssPassageAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(issPassageAdapter);
        recyclerView.setLayoutManager(layoutManager);
        viewModel = ViewModelProviders.of(this, factory).get(IssPassageViewModel.class);
        issPassagesMLD = viewModel.getIssPassages();
        subscribe();


    }

    private void subscribe() {
        Observer<List<IssPassage>> issPassageObserver = new Observer<List<IssPassage>>() {
            @Override
            public void onChanged(@Nullable List<IssPassage> issPassages) {
                if (issPassages != null && issPassages.size() > 0) {
                    IssPassage firstPassage = issPassages.get(0);
                    int firstDuration = firstPassage.getDuration();
                    if (firstDuration == IssPassage.ISS_DURATION_FAILED_CONNECTION) {
                        recyclerView.setVisibility(View.GONE);
                        resultTextMessage.setText(getString(R.string.iss_passage_result_no_connection));
                    } else if (firstDuration == IssPassage.ISS_DURATION_NO_DATA) {
                        recyclerView.setVisibility(View.GONE);
                        resultTextMessage.setText(getString(R.string.iss_passage_result_no_data));
                    } else {
                        issPassageAdapter.setIssPassages(issPassages);
                        String formatString = getString(R.string.iss_passage_result_success);
                        String message = String.format(formatString, latitude, longitude);
                        resultTextMessage.setText(message);
                    }
                }

            }
        };

        issPassagesMLD.observe(this, issPassageObserver);
    }
}
