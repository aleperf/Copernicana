package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.BuildConfig;
import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.GlideApp;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.utilities.Utils;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ApodDetailFragment extends Fragment {

    private final static String TAG = ApodDetailFragment.class.getSimpleName();
    public final static String APOD_DATE = "apod extra date";
    private static final String APOD_TITLE = "apod extra title";
    public final static String APOD_DEFAULT_DATE = "DEFAULT_DATE";


    private Unbinder unbinder;
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


    private ApodViewModel apodViewModel;
    private LiveData<Apod> apod;
    private String date;


    @Inject
    ViewModelProvider.Factory factory;

    public ApodDetailFragment() {
        //default empty constructor
    }

    public static ApodDetailFragment getInstance(String date) {
        Bundle bundle = new Bundle();
        bundle.putString(APOD_DATE, date);
        ApodDetailFragment fragment = new ApodDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) getActivity().getApplication()).getCopernicanaApplicationComponent().inject(this);
        setRetainInstance(true);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            date = bundle.getString(APOD_DATE, APOD_DEFAULT_DATE);
        } else {
            date = savedInstanceState.getString(APOD_DATE, APOD_DEFAULT_DATE);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.apod_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apodViewModel = ViewModelProviders.of(this, factory).get(ApodViewModel.class);
        apod = apodViewModel.getApodWithDate(date);
        subscribeApod();
    }

    private void subscribeApod() {
        Observer<Apod> apodObserver = new Observer<Apod>() {
            @Override
            public void onChanged(@Nullable Apod apod) {
                if (apod != null) {
                    String imageUrl = null;
                    String mediaType = apod.getMediaType();
                    if (mediaType.equals(Apod.MEDIA_TYPE_IMAGE)) {
                        imageUrl = apod.getUrl();
                        videoPlayerSymbol.setVisibility(View.INVISIBLE);

                    } else if (mediaType.equals(Apod.MEDIA_TYPE_VIDEO)) {
                        videoPlayerSymbol.setVisibility(View.VISIBLE);
                        String videoUrl = apod.getUrl();
                        String videoId = Utils.getYoutubeIdFromUrl(videoUrl);
                        setVideoPlayerOnClickListener(videoId);
                        imageUrl = Utils.buildVideoThumbnailFromId(videoId);
                    }
                    GlideApp.with(getActivity())
                            .load(imageUrl)
                            .placeholder(R.drawable.nasa_43566_unsplash)
                            .error(R.drawable.nasa_43566_unsplash)
                            .into(apodImage);
                    apodTitle.setText(apod.getTitle());
                    apodExplanation.setText(apod.getExplanation());
                    String formattedDate = Utils.getFormattedDate(apod.getDate(), getActivity());
                    String dateString = String.format(getString(R.string.apod_detail_date), formattedDate);
                    apodDate.setText(dateString);
                    if (apod.getCopyright() != null) {
                        String copyright = String.format(getString(R.string.apod_copyright_label),
                                apod.getCopyright());
                        apodCopyright.setVisibility(View.VISIBLE);
                        apodCopyright.setText(copyright);
                    } else {
                        apodCopyright.setVisibility(View.INVISIBLE);
                    }
                }
            }
        };
        apod.observe(this, apodObserver);
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


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(APOD_DATE, date);

    }


}
