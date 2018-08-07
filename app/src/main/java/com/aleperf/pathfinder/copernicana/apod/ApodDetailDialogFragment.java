package com.aleperf.pathfinder.copernicana.apod;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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

public class ApodDetailDialogFragment extends DialogFragment {

    private static final String APOD_EXTRA = "apod extra";

    public ApodDetailDialogFragment(){
        //empty constructor
    }

    public static ApodDetailDialogFragment newInstance(Apod apod){
        Bundle bundle = new Bundle();
        bundle.putParcelable(APOD_EXTRA, apod);
        ApodDetailDialogFragment fragment = new ApodDetailDialogFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    public static int IS_FAVORITE = 1;
    public static int NOT_FAVORITE = 0;


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
    @BindView(R.id.apod_detail_content)
    ConstraintLayout apodDetailView;

    private ApodViewModel apodViewModel;
    private Apod apod;


    @Inject
    ViewModelProvider.Factory factory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) getActivity().getApplication()).getCopernicanaApplicationComponent().inject(this);
        setRetainInstance(true);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            apod = bundle.getParcelable(APOD_EXTRA);
        } else {
            apod = savedInstanceState.getParcelable(APOD_EXTRA);
        }



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_apod_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        fillApodFields(apod);
        setFavoriteOnClickListener();
        setSharingOnClickListener();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apodViewModel = ViewModelProviders.of(this, factory).get(ApodViewModel.class);
    }

    private void fillApodFields(Apod apod) {
        setCorrectMediaTypeImage(apod);
        apodTitle.setText(apod.getTitle());
        apodExplanation.setText(apod.getExplanation());
        setFormattedDate(apod);
        setCopyrightField(apod);
        setIsFavoriteIcon(apod);

    }

    private void setCorrectMediaTypeImage(Apod apod) {
        String imageUrl;
        String mediaType = apod.getMediaType();
        if (mediaType.equals(Apod.MEDIA_TYPE_IMAGE)) {
            imageUrl = apod.getUrl();
            videoPlayerSymbol.setVisibility(View.INVISIBLE);
            loadImageWithGlide(imageUrl);

        } else if (mediaType.equals(Apod.MEDIA_TYPE_VIDEO)) {
            if (apod.getUrl().contains("youtube")) {
                videoPlayerSymbol.setVisibility(View.VISIBLE);
                String videoUrl = apod.getUrl();
                String videoId = Utils.getYoutubeIdFromUrl(videoUrl);
                setVideoPlayerOnClickListener(videoId);
                imageUrl = Utils.buildVideoThumbnailFromId(videoId);
                loadImageWithGlide(imageUrl);
            }else {
                videoPlayerSymbol.setVisibility(View.INVISIBLE);
                //TODO create an image for not playable video
                apodImage.setImageResource(R.drawable.nasa_43566_unsplash);
            }
        }
    }

    private void loadImageWithGlide(String imageUrl){
        GlideApp.with(getActivity())
                .load(imageUrl)
                .placeholder(R.drawable.apod_placeholder)
                .error(R.drawable.apod_error)
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

    private void setFavoriteOnClickListener() {

        apodFavIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String message;
                if (apod != null) {
                    if (apod.getIsFavorite() == NOT_FAVORITE) {
                        apod.setIsFavorite(IS_FAVORITE);
                        apodViewModel.updateApod(IS_FAVORITE, apod.getDate());
                        apodFavIcon.setImageResource(R.drawable.star_icon);
                        message = getString(R.string.apod_add_to_favorite);
                    } else {
                        apod.setIsFavorite(NOT_FAVORITE);
                        apodViewModel.updateApod(NOT_FAVORITE, apod.getDate());
                        apodFavIcon.setImageResource(R.drawable.star_icon_default);
                        message = getString(R.string.apod_remove_from_favorite);
                    }
                    Snackbar.make(apodDetailView, message, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setSharingOnClickListener() {
        apodShareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.apod_share_apod));
                String message = String.format(getString(R.string.apod_share_message), apod.getTitle(), apod.getUrl());
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, getString(R.string.apod_share_copernicana)));
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        Resources res = getResources();
        int width = res.getDimensionPixelSize(R.dimen.dialog_fragment_width);
        int height = res.getDimensionPixelSize(R.dimen.dialog_fragment_height);
        getDialog().getWindow().setLayout(width, height);

    }

    @Override
    public void onDestroyView() {

        Dialog dialog = getDialog();
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(APOD_EXTRA, apod);

    }


}
