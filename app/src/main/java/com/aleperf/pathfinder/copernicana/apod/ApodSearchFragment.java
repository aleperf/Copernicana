package com.aleperf.pathfinder.copernicana.apod;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ApodSearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    private static final int MIN_YEAR = 1995;
    private static final int MIN_MONTH = 5; // it's june
    private static final int MIN_DAY = 16;

    @BindView(R.id.apod_search_select_button)
    Button searchButton;
    @BindView(R.id.date_selected_text_view)
    TextView dateSelected;
    @BindView(R.id.search_result_text_view)
    TextView searchResultTextView;
    @BindView(R.id.apod_search_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.apod_detail_content)
    ConstraintLayout apodDetailContent;


    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) this.getActivity().getApplication())
                .getCopernicanaApplicationComponent().inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_apod_search, container, false);
        unbinder =  ButterKnife.bind(this, rootView);
        setSearchButtonOnClickListener();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setSearchButtonOnClickListener(){
        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Calendar minDate = Calendar.getInstance();
                minDate.set(MIN_YEAR, MIN_MONTH, MIN_DAY);
                long minDateMillis = minDate.getTimeInMillis();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), ApodSearchFragment.this,
                        year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - 1000);

                datePickerDialog.getDatePicker().setMinDate(minDateMillis);
                datePickerDialog.show();
            }
        };
        searchButton.setOnClickListener(buttonListener);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Toast.makeText(getActivity(), "selected year " + year + " month " + month + " day: " + dayOfMonth,
                Toast.LENGTH_SHORT).show();
    }
}
