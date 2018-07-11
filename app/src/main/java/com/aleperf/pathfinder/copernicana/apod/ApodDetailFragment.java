package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.BuildConfig;
import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.GlideApp;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.utilities.Utils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ApodDetailFragment extends Fragment {

    private final static String TAG = ApodDetailFragment.class.getSimpleName();
    public final static String APOD_DATE = "apod date";
    public final static String APOD_DEFAULT_DATE = "DEFAULT_DATE";
    private final static String PLAYER_POSITION = "player position";

    private Unbinder unbinder;
    @BindView(R.id.apod_image)
    ImageView apodImage;
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    @BindView(R.id.image_group)
    Group imageGroup;
    @BindView(R.id.video_group)
    Group videoGroup;
    @BindView(R.id.apod_title)
    TextView apodTitle;
    @BindView(R.id.apod_explanation)
    TextView apodExplanation;
    @BindView(R.id.apod_date)
    TextView apodDate;
    @BindView(R.id.apod_copyright)
    TextView apodCopyright;
    @BindView(R.id.youtube_player_container)
    FrameLayout youtubePlayerContainer;
    private ApodViewModel apodViewModel;
    private LiveData<Apod> apod;
    private YouTubePlayer youTubePlayer;
    private int playerposition;

    @Inject
    ViewModelProvider.Factory factory;

    public ApodDetailFragment(){
        //default empty constructor
    }

    public static ApodDetailFragment getInstance(String date){
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
        apod = apodViewModel.getLastApod();
        subscribeApod();
    }

    private void subscribeApod(){
        Observer<Apod> apodObserver = new Observer<Apod>() {
            @Override
            public void onChanged(@Nullable Apod apod) {
                  if(apod != null){
                      String mediaType = apod.getMediaType();
                      if(mediaType.equals(Apod.MEDIA_TYPE_IMAGE)){
                          Log.d("uffa", "type is image");
                          imageGroup.setVisibility(View.VISIBLE);
                          videoGroup.setVisibility(View.GONE);
                          GlideApp.with(getActivity())
                                  .load(apod.getUrl())
                                  .placeholder(R.drawable.nasa_43566_unsplash)
                                  .error(R.drawable.nasa_43566_unsplash)
                                  .into(apodImage);

                      } else if(mediaType.equals(Apod.MEDIA_TYPE_VIDEO)){
                          imageGroup.setVisibility(View.GONE);
                          videoGroup.setVisibility(View.VISIBLE);
                          String videoUrl = apod.getUrl();
                          String videoId = Utils.getYoutubeIdFromUrl(videoUrl);
                          initializeYoutubePlayer(videoId);
                      }
                      apodTitle.setText(apod.getTitle());
                      apodExplanation.setText(apod.getExplanation());
                      apodDate.setText(apod.getDate());
                      if(apod.getCopyright() != null){
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

    private void initializeYoutubePlayer(String videoId){
        youTubePlayerFragment = new YouTubePlayerSupportFragment();
        youTubePlayerFragment.initialize(BuildConfig.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                                boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer = player;
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.cueVideo(videoId);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {

                //print or show error if initialization failed
                Log.e(TAG, "Youtube Player View initialization failed");
            }
        });

        getFragmentManager().beginTransaction()
                .replace(R.id.youtube_player_container, youTubePlayerFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(youTubePlayer != null){
            playerposition = youTubePlayer.getCurrentTimeMillis();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
