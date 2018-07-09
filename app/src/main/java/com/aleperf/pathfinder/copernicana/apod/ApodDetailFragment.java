package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ApodDetailFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.apod_image)
    private ImageView apodImage;
    @BindView(R.id.youtube_player_fragment)
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    @BindView(R.id.apod_title)
    TextView apodTitle;
    @BindView(R.id.apod_explanation)
    TextView apodExplanation;
    @BindView(R.id.apod_date)
    TextView apodDate;
    ApodViewModel apodViewModel;
    LiveData<Apod> apod;

    @Inject
    ViewModelProvider.Factory factory;

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
    }

    private void subscribeApod(){
        Observer<Apod> apodObserver = new Observer<Apod>() {
            @Override
            public void onChanged(@Nullable Apod apod) {

            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
