package com.aleperf.pathfinder.copernicana.apod;

import android.app.DatePickerDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aleperf.pathfinder.copernicana.BuildConfig;
import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.GlideApp;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.utilities.Utils;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ApodSearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    private static final int MIN_YEAR = 1995;
    private static final int MIN_MONTH = 5; // it's june
    private static final int MIN_DAY = 16;
    private static final String LAST_APOD_SEARCHED = "apod extra";
    private static final String SEARCH_RESULT_MESSAGE = "search result message";
    private static final String SEARCH_DATE = "search date";
    private static final String FORMATTED_SEARCH_DATE = "formatted search date";
    private static final String APOD_FOUND = "apod found";
    private static final String IS_ADDED_APOD_TO_DATABASE = "is added apod to database";
    public static int IS_FAVORITE = 1;
    public static int NOT_FAVORITE = 0;

    @BindView(R.id.apod_search_select_button)
    Button searchButton;
    @BindView(R.id.date_selected_text_view)
    TextView dateSelected;
    @BindView(R.id.search_result_text_view)
    TextView searchResultTextView;
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
    @BindView(R.id.apod_detail_add_remove_icon)
    ImageView apodAddRemoveIcon;

    private String searchDate;
    private String formattedDate;
    private String resultMessage;
    private Apod lastApodSearched;
    private boolean apodFound = false;
    private boolean isAdded = false;
    private MutableLiveData<Apod> searchApodMutableLiveData;

    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    Context copernicanaApplicationContext;
    private ApodSearchViewModel apodSearchViewModel;


    private Unbinder unbinder;

    public ApodSearchFragment(){
        //default empty constructor
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
        View rootView = inflater.inflate(R.layout.fragment_apod_search, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setSearchButtonOnClickListener();
        if (savedInstanceState != null) {
            lastApodSearched = savedInstanceState.getParcelable(LAST_APOD_SEARCHED);
            resultMessage = savedInstanceState.getString(SEARCH_RESULT_MESSAGE);
            searchDate = savedInstanceState.getString(SEARCH_DATE);
            formattedDate = savedInstanceState.getString(FORMATTED_SEARCH_DATE);
            apodFound = savedInstanceState.getBoolean(APOD_FOUND);
            isAdded = savedInstanceState.getBoolean(IS_ADDED_APOD_TO_DATABASE);
        }
        if (apodFound) {
            if (lastApodSearched != null) {
                apodDetailContent.setVisibility(View.VISIBLE);
                fillApodFields(lastApodSearched);
            }
        } else {
            apodDetailContent.setVisibility(View.GONE);
        }
        if (resultMessage != null) {
            searchResultTextView.setText(resultMessage);
        }
        if (formattedDate != null) {
            dateSelected.setText(formattedDate);
        }
        apodAddRemoveIcon.setVisibility(View.VISIBLE);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apodSearchViewModel = ViewModelProviders.of(this, factory).get(ApodSearchViewModel.class);
        searchApodMutableLiveData = apodSearchViewModel.getApodSearched();
        subscribeSearchApod();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LAST_APOD_SEARCHED, lastApodSearched);
        outState.putString(SEARCH_RESULT_MESSAGE, resultMessage);
        outState.putString(SEARCH_DATE, searchDate);
        outState.putString(FORMATTED_SEARCH_DATE, formattedDate);
        outState.putBoolean(APOD_FOUND, apodFound);
        outState.putBoolean(IS_ADDED_APOD_TO_DATABASE, isAdded);
    }

    public void setSearchButtonOnClickListener() {
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
        searchDate = Utils.getDateSearchString(year, month, dayOfMonth);
        formattedDate = Utils.getFormattedDate(searchDate, getActivity());
        dateSelected.setText(formattedDate);
        isAdded = false;
        lastApodSearched = null;
        apodSearchViewModel.searchApodForDate(searchDate);
    }

    private void subscribeSearchApod() {
        Observer<Apod> searchApodObserver = new Observer<Apod>() {
            @Override
            public void onChanged(@Nullable Apod apod) {
                if (apod != null) {
                    if (setCorrectResultTextView(apod)) {
                        Log.d("uffa", "sono in observer apod");
                        apodFound = true;
                        lastApodSearched = apod;
                        apodDetailContent.setVisibility(View.VISIBLE);
                        isAdded = apodSearchViewModel.isInDatabase();
                        fillApodFields(apod);
                    } else {
                        apodDetailContent.setVisibility(View.GONE);
                        apodFound = false;
                    }
                }

            }
        };

        searchApodMutableLiveData.observe(this, searchApodObserver);

    }

    /**
     * Set the correct  text for the searchResultTextView and return a boolean representing if
     * an Apod was found
     *
     * @param apod the result of the search
     * @return true if an Apod was found, false otherwise.
     */

    private boolean setCorrectResultTextView(Apod apod) {
        searchResultTextView.setVisibility(View.VISIBLE);
        //failed data retrieval
        if (apod.getTitle().equals(AstroRepository.FAILED_CONNECTION)) {
            searchResultTextView.setText(getString(R.string.search_failed_connection));
            return false;
        }
        //result found for the correct date
        if (apod.getDate().equals(searchDate)) {
            searchResultTextView.setText(getString(R.string.search_result_found));
            return true;
        }
        // result found for the closest date
        String closest = String.format(getString(R.string.search_result_closest), formattedDate);
        searchResultTextView.setText(closest);
        return true;
    }

    private void fillApodFields(Apod apod) {
        setCorrectMediaTypeImage(apod);
        apodTitle.setText(apod.getTitle());
        apodExplanation.setText(apod.getExplanation());
        //it this isn't the latest Apod in the database allow to add remove the Apod
        if (!isLatestApodDate(apod.getDate())) {
            apodAddRemoveIcon.setVisibility(View.VISIBLE);
            if (isAdded) {
                apodAddRemoveIcon.setImageResource(R.drawable.trash_delete);
            } else {
                apodAddRemoveIcon.setImageResource(R.drawable.add_photo);
            }
        } else {
            //if this is the Apod of the Day, it can't be removed from the database
            apodAddRemoveIcon.setVisibility(View.INVISIBLE);
        }
        setFormattedDate(apod);
        setCopyrightField(apod);
        setIsFavoriteIcon(apod);
        setAddToFavoritesOnClickListener();
        setSharingOnClickListener();
        setAddRemoveOnClickListener();
    }

    private void setCorrectMediaTypeImage(Apod apod) {
        String imageUrl = null;
        String mediaType = apod.getMediaType();
        if (mediaType.equals(Apod.MEDIA_TYPE_IMAGE)) {
            imageUrl = apod.getUrl();
            videoPlayerSymbol.setVisibility(View.INVISIBLE);
            videoPlayerSymbol.setOnClickListener(null);
            apodImage.setOnClickListener(null);
            loadImageWithGlide(imageUrl);


        } else if (mediaType.equals(Apod.MEDIA_TYPE_VIDEO)) {
            String videoUrl = apod.getUrl();
            if (videoUrl.contains("youtube")) {
                videoPlayerSymbol.setVisibility(View.VISIBLE);
                String videoId = Utils.getYoutubeIdFromUrl(videoUrl);
                setVideoPlayerOnClickListener(videoId);
                imageUrl = Utils.buildVideoThumbnailFromId(videoId);
                loadImageWithGlide(imageUrl);
            } else {
                videoPlayerSymbol.setVisibility(View.INVISIBLE);
                //TODO create an image for not playable video
                apodImage.setImageResource(R.drawable.nasa_43566_unsplash);
                videoPlayerSymbol.setOnClickListener(null);
                apodImage.setOnClickListener(null);
            }
        }

    }

    private void loadImageWithGlide(String imageUrl) {
        GlideApp.with(getActivity())
                .load(imageUrl)
                .placeholder(R.drawable.nasa_43566_unsplash)
                .error(R.drawable.nasa_43566_unsplash)
                .into(apodImage);
    }


    private void setFormattedDate(Apod apod) {
        String formattedDate = Utils.getFormattedDate(apod.getDate(), getActivity());
        String dateString = String.format(getString(R.string.apod_detail_date), formattedDate);
        apodDate.setText(dateString);
    }

    private void setCopyrightField(Apod apod) {
        if (apod.getCopyright() != null) {
            String copyright = String.format(getString(R.string.apod_copyright_label),
                    apod.getCopyright());
            apodCopyright.setVisibility(View.VISIBLE);
            apodCopyright.setText(copyright);
        } else {
            apodCopyright.setVisibility(View.INVISIBLE);
        }
    }

    private void setIsFavoriteIcon(Apod apod) {
        if (apod.getIsFavorite() == IS_FAVORITE) {
            apodFavIcon.setImageResource(R.drawable.star_icon);
        } else {
            apodFavIcon.setImageResource(R.drawable.star_icon_default);
        }
    }


    private void setAddToFavoritesOnClickListener() {
        apodFavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                if (lastApodSearched.getIsFavorite() == NOT_FAVORITE) {
                    lastApodSearched.setIsFavorite(IS_FAVORITE);
                    apodSearchViewModel.addApodToTheDatabase(lastApodSearched);
                    isAdded = true;
                    apodAddRemoveIcon.setImageResource(R.drawable.trash_delete);
                    apodFavIcon.setImageResource(R.drawable.star_icon);
                    message = getString(R.string.search_snack_bar_add_apod_to_fav);
                } else {
                    lastApodSearched.setIsFavorite(NOT_FAVORITE);
                    apodFavIcon.setImageResource(R.drawable.star_icon_default);
                    apodSearchViewModel.updateApodInDatabase(NOT_FAVORITE, lastApodSearched.getDate());
                    message = getString(R.string.search_snack_bar_unfavorite);
                }
                Snackbar.make(apodDetailContent, message, Snackbar.LENGTH_LONG).show();
            }
        });

    }


    private void setVideoPlayerOnClickListener(String videoId) {
        View[] views = {videoPlayerSymbol, apodImage};
        for (View view : views) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchVideoPlayerIntent = YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                            BuildConfig.YOUTUBE_API_KEY, videoId);
                    startActivity(launchVideoPlayerIntent);
                }
            });
        }
    }

    private void setSharingOnClickListener() {
        apodShareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.apod_share_apod));
                String message = String.format(getString(R.string.apod_share_message),
                        lastApodSearched.getTitle(), lastApodSearched.getUrl());
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, getString(R.string.apod_share_copernicana)));
            }
        });
    }

    private void setAddRemoveOnClickListener() {
        apodAddRemoveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                if (!isAdded) {
                    isAdded = true;
                    apodSearchViewModel.addApodToTheDatabase(lastApodSearched);
                    apodAddRemoveIcon.setImageResource(R.drawable.trash_delete);
                    message = getString(R.string.search_snack_bar_image_added);


                } else {
                    isAdded = false;
                    if (lastApodSearched.getIsFavorite() == IS_FAVORITE) {
                        lastApodSearched.setIsFavorite(NOT_FAVORITE);
                        apodFavIcon.setImageResource(R.drawable.star_icon_default);
                    }
                    apodAddRemoveIcon.setImageResource(R.drawable.add_photo);
                    apodSearchViewModel.removeApodFromDatabase(lastApodSearched.getDate());

                    message = getString(R.string.search_snack_bar_remove);
                }
                Snackbar.make(apodDetailContent, message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Check if the current Apod is the latest Apod
     * @param date the date of the current Apod
     * @return true if it is the latest Apod in database, false otherwise
     */
    private boolean isLatestApodDate(String date) {
        SharedPreferences sharedPref = copernicanaApplicationContext.getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        String defaultValue = getString(R.string.preference_latest_apod_default_value);
        String key = getString(R.string.preference_latest_apod_date_key);
        String latestApod = sharedPref.getString(key, defaultValue);
        if(date.equals(latestApod)){
            return true;
        }
        return false;

    }


}
