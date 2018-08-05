package com.aleperf.pathfinder.copernicana.epic;

import android.app.DatePickerDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;
import com.aleperf.pathfinder.copernicana.utilities.Utils;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class EpicSearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private final static int SEARCH_TYPE_NATURAL = 0;
    private final static int SEARCH_TYPE_ENHANCED = 1;
    private static final int MIN_YEAR = 2015;
    private static final int MIN_MONTH = 5; // it's june
    private static final int MIN_DAY = 13;
    private final static String CURRENT_SEARCH_TYPE = "current search type";
    private final static String CURRENT_DATE_SELECTED = "current date selected";
    private final static String CURRENT_MESSAGE = "current message";
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.radio_natural)
    RadioButton naturalRadioButton;
    @BindView(R.id.radio_enhanced)
    RadioButton enhancedRadioButton;
    @BindView(R.id.epic_search_button)
    Button searchButton;
    @BindView(R.id.epic_search_date_selected)
    TextView dateSelectedTextView;
    @BindView(R.id.epic_search_message)
    TextView searchResultMessageTextView;
    @BindView(R.id.search_results_recycler_view)
    RecyclerView resultsRecyclerView;
    private EpicAdapter naturalAdapter;
    private EpicEnhancedAdapter enhancedAdapter;
    @Inject
    ViewModelProvider.Factory factory;
    private EpicSearchViewModel viewModel;
    MutableLiveData<List<Epic>> epicNaturalLiveData;
    MutableLiveData<List<EpicEnhanced>> epicEnhancedMutableLiveData;
    Observer<List<Epic>> naturalObserver;
    Observer<List<EpicEnhanced>> enhancedObserver;
    Unbinder unbinder;
    String date;
    String message;

    private int searchType;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) this.getActivity().getApplication())
                .getCopernicanaApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_epic_search, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        int columnCount = getResources().getInteger(R.integer.column_count);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), columnCount);
        resultsRecyclerView.setLayoutManager(gridLayoutManager);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_enhanced) {
                    searchType = SEARCH_TYPE_ENHANCED;
                } else {
                    searchType = SEARCH_TYPE_NATURAL;
                }
            }
        });
        if (savedInstanceState != null) {
            searchType = savedInstanceState.getInt(CURRENT_SEARCH_TYPE);
        }
        if (searchType == SEARCH_TYPE_NATURAL) {
            naturalRadioButton.setChecked(true);
            enhancedRadioButton.setChecked(false);
        } else {
            naturalRadioButton.setChecked(false);
            enhancedRadioButton.setChecked(true);
        }
        setButtonSearchOnClickListener();
        String noDate = getString(R.string.epic_search_no_date);
        String defaultMessage = getString(R.string.epic_search_default_message);
        if (savedInstanceState != null) {
            date = savedInstanceState.getString(CURRENT_DATE_SELECTED, noDate);
            message = savedInstanceState.getString(CURRENT_MESSAGE, defaultMessage);

        } else {
            date = noDate;
            message = defaultMessage;
        }
        dateSelectedTextView.setText(date);
        searchResultMessageTextView.setText(message);


        return rootView;
    }

    private void setButtonSearchOnClickListener() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchType == SEARCH_TYPE_NATURAL) {
                    if (naturalAdapter == null) {
                        naturalAdapter = new EpicAdapter(getActivity(), EpicAdapter.FLAG_FROM_SEARCH);

                    }
                    resultsRecyclerView.setAdapter(naturalAdapter);
                    unsubscribeEnhanced();
                    observeNatural();
                } else {
                    if (enhancedAdapter == null) {
                        enhancedAdapter = new EpicEnhancedAdapter(getActivity(), EpicEnhancedAdapter.FLAG_FROM_SEARCH);
                    }
                    resultsRecyclerView.setAdapter(enhancedAdapter);
                    unsubscribeNatural();
                    observeEnhanced();
                }
                launchDatePicker();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(EpicSearchViewModel.class);
        epicNaturalLiveData = viewModel.getNaturalEpic();
        epicEnhancedMutableLiveData = viewModel.getEnhancedEpic();

        if (searchType == SEARCH_TYPE_NATURAL) {
            naturalAdapter = new EpicAdapter(getActivity(), EpicAdapter.FLAG_FROM_SEARCH);
            enhancedAdapter = null;
            resultsRecyclerView.setAdapter(naturalAdapter);
            unsubscribeEnhanced();
            observeNatural();

        } else {
            enhancedAdapter = new EpicEnhancedAdapter(getActivity(), EpicEnhancedAdapter.FLAG_FROM_SEARCH);
            naturalAdapter = null;
            unsubscribeNatural();
            observeEnhanced();
            resultsRecyclerView.setAdapter(enhancedAdapter);
        }

    }

    private void observeNatural() {
        naturalObserver = new Observer<List<Epic>>() {
            @Override
            public void onChanged(@Nullable List<Epic> epics) {
                if (searchType == SEARCH_TYPE_NATURAL) {
                    if (epics != null && epics.size() > 0) {
                        Epic firstEpic = epics.get(0);
                        String messageResult = firstEpic.getCaption();
                        if (messageResult.equals(AstroRepository.EPIC_NO_DATA)) {
                            resultsRecyclerView.setVisibility(View.GONE);
                            searchResultMessageTextView.setVisibility(View.VISIBLE);
                            message = getString(R.string.epic_search_result_no_data);
                            searchResultMessageTextView.setText(message);
                        } else if (messageResult.equals(AstroRepository.EPIC_FAILURE_ON_CONNECTION)) {
                            resultsRecyclerView.setVisibility(View.GONE);
                            searchResultMessageTextView.setVisibility(View.VISIBLE);
                            message = getString(R.string.epic_search_result_no_connection);
                            searchResultMessageTextView.setText(message);
                        } else {
                            resultsRecyclerView.setVisibility(View.VISIBLE);
                            message = getString(R.string.epic_search_result_success);
                            searchResultMessageTextView.setText(message);
                            searchResultMessageTextView.setVisibility(View.VISIBLE);
                            if (naturalAdapter != null) {
                                naturalAdapter.setEpic(epics);
                            }
                        }

                    }

                }
            }
        };
        epicNaturalLiveData.observe(this, naturalObserver);
    }

    private void observeEnhanced() {
        enhancedObserver = new Observer<List<EpicEnhanced>>() {
            @Override
            public void onChanged(@Nullable List<EpicEnhanced> epicEnhanceds) {
                if (searchType == SEARCH_TYPE_ENHANCED) {
                    if (epicEnhanceds != null && epicEnhanceds.size() > 0) {
                        EpicEnhanced firstEpicEnhanced = epicEnhanceds.get(0);
                        String messageResult = firstEpicEnhanced.getCaption();
                        if (messageResult.equals(AstroRepository.EPIC_NO_DATA)) {
                            resultsRecyclerView.setVisibility(View.GONE);
                            message = getString(R.string.epic_search_result_no_data);
                            searchResultMessageTextView.setText(message);
                        } else if (messageResult.equals(AstroRepository.EPIC_FAILURE_ON_CONNECTION)) {
                            resultsRecyclerView.setVisibility(View.GONE);
                            message = getString(R.string.epic_search_result_no_connection);
                            searchResultMessageTextView.setText(message);
                        } else {
                            resultsRecyclerView.setVisibility(View.VISIBLE);
                            message = getString(R.string.epic_search_result_success);
                            searchResultMessageTextView.setText(message);

                            if (enhancedAdapter != null) {
                                enhancedAdapter.setEpicEnhanced(epicEnhanceds);
                            }
                        }
                    }
                }
            }
        };

        epicEnhancedMutableLiveData.observe(this, enhancedObserver);
    }

    private void unsubscribeNatural() {
        if (naturalObserver != null) {
            epicNaturalLiveData.removeObserver(naturalObserver);
        }
    }

    private void unsubscribeEnhanced() {
        if (enhancedObserver != null) {
            epicEnhancedMutableLiveData.removeObserver(enhancedObserver);
        }
    }

    private void launchDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar minDate = Calendar.getInstance();
        minDate.set(MIN_YEAR, MIN_MONTH, MIN_DAY);
        long minDateMillis = minDate.getTimeInMillis();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), EpicSearchFragment.this,
                year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - 1000);

        datePickerDialog.getDatePicker().setMinDate(minDateMillis);
        datePickerDialog.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_SEARCH_TYPE, searchType);
        outState.putString(CURRENT_DATE_SELECTED, date);
        outState.putString(CURRENT_MESSAGE, message);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String searchDate = Utils.getDateSearchString(year, month, dayOfMonth);
        date = Utils.getFormattedDate(searchDate, getActivity());
        dateSelectedTextView.setText(date);
        if (searchType == SEARCH_TYPE_NATURAL) {
            viewModel.searchNaturalEpic(searchDate);
        } else {
            viewModel.searchEnhancedEpic(searchDate);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unsubscribeNatural();
        unsubscribeEnhanced();
    }
}
