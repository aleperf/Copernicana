package com.aleperf.pathfinder.copernicana.apod;

import android.app.DatePickerDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.utilities.Utils;

import java.util.Calendar;

import javax.inject.Inject;

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
    @BindView(R.id.apod_detail_image)
    ImageView apodImage;
    @BindView(R.id.apod_detail_title)
    TextView apodTitle;
    @BindView(R.id.apod_detail_explanation)
    TextView apodExplanation;
    @BindView(R.id.apod_detail_date)
    TextView apodDate;
    @BindView(R.id.apod_detail_copyright)
    TextView apodCopyright;
    @BindView(R.id.apod_detail_video_player_ico)
    ImageView videoPlayerSymbol;
    @BindView(R.id.apod_detail_fav_icon)
    ImageView apodFavIcon;
    @BindView(R.id.apod_detail_share_icon)
    ImageView apodShareIcon;

    private String searchDate;
    private String formattedDate;
    private String resultMessage;

    private MutableLiveData<Apod> searchApodMutableLiveData;
    @Inject
    ViewModelProvider.Factory factory;
    private ApodSearchViewModel apodSearchViewModel;



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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apodSearchViewModel = ViewModelProviders.of(this, factory).get(ApodSearchViewModel.class);
        searchApodMutableLiveData = apodSearchViewModel.getApodSearched();

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
        searchDate = Utils.getDateSearchString(year, month, dayOfMonth);
        formattedDate = Utils.getFormattedDate(searchDate, getActivity());
        dateSelected.setText(formattedDate);
        apodSearchViewModel.searchApodForDate(searchDate);
    }

    private void subscribeSearchApod(){
        Observer<Apod> searchApodObserver = new Observer<Apod>() {
            @Override
            public void onChanged(@Nullable Apod apod) {
                if(apod != null){
                  if(setCorrectResultTextView(apod)){
                      apodDetailContent.setVisibility(View.VISIBLE);
                      fillApodFields(apod);
                  } else {
                      apodDetailContent.setVisibility(View.GONE);
                  }
                }

            }
        };

       searchApodMutableLiveData.observe(this, searchApodObserver);

    }

    /**
     * Set the correct  text for the searchResultTextView and return a boolean representing if
     * an Apod was found
     * @param apod the result of the search
     * @return true if an Apod was found, false otherwise.
     */

    private boolean setCorrectResultTextView(Apod apod){
        searchResultTextView.setVisibility(View.VISIBLE);
        //failed data retrieval
        if(apod.getTitle().equals(AstroRepository.FAILED_CONNECTION)){
            searchResultTextView.setText(getString(R.string.search_failed_connection));
            return false;
        }
        //result found for the correct date
        if(apod.getDate().equals(searchDate)){
            searchResultTextView.setText(getString(R.string.search_result_found));
            return true;
        }
        // result found for the closest date
        String closest = String.format(getString(R.string.search_result_closest), formattedDate);
        searchResultTextView.setText(closest);
        return true;
        }

        private void fillApodFields(Apod apod){

        }
}
