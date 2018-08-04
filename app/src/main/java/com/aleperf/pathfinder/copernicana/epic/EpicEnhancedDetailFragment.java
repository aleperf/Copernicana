package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.GlideApp;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;
import com.aleperf.pathfinder.copernicana.utilities.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EpicEnhancedDetailFragment extends Fragment {
    private final static String CURRENT_EPIC = "current natural epic";
    private final static int IS_FAVORITE = 1;
    private final static int NOT_FAVORITE = 0;

    private Epic epic;
    private Unbinder unbinder;
    @BindView(R.id.epic_detail_image)
    ImageView epicImage;
    @BindView(R.id.epic_detail_cl)
    ConstraintLayout constraintLayout;
    @BindView(R.id.epic_detail_caption)
    TextView caption;
    @BindView(R.id.epic_date_and_time_value)
    TextView dateAndTime;
    @BindView(R.id.epic_distance_to_earth_value)
    TextView distanceToEarth;
    @BindView(R.id.epic_distance_to_sun_value)
    TextView distanceToSun;
    @BindView(R.id.epic_sun_to_earth_value)
    TextView sunToEarth;
    @BindView(R.id.epic_detail_centroid_lat_value)
    TextView centroidLatitude;
    @BindView(R.id.epic_detail_centroid_lon_value)
    TextView centroidLongitude;
    @BindView(R.id.epic_detail_fav_icon)
    ImageView favoriteIcon;
    @BindView(R.id.epic_detail_share_icon)
    ImageView shareIcon;
    EpicEnhancedDetailViewModel viewModel;

    @Inject
    ViewModelProvider.Factory factory;

    public EpicEnhancedDetailFragment() {
    }


    public static EpicEnhancedDetailFragment getInstance(EpicEnhanced epicEnhanced) {
        Log.d("uffa", "sono in getInstance di EpicEnhancedDetailFragment");
        Bundle bundle = new Bundle();
        bundle.putParcelable(CURRENT_EPIC, epicEnhanced);
        EpicEnhancedDetailFragment fragment = new EpicEnhancedDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) this.getActivity().getApplication())
                .getCopernicanaApplicationComponent().inject(this);
        if (savedInstanceState == null) {
            epic = getArguments().getParcelable(CURRENT_EPIC);
        } else {
            epic = savedInstanceState.getParcelable(CURRENT_EPIC);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("uffa", "sono in onCreateView di EpicEnhancedDetailFragment");
        View rootView = inflater.inflate(R.layout.epic_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        viewModel = ViewModelProviders.of(this, factory).get(EpicEnhancedDetailViewModel.class);
        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewModel != null){
                    String message;
                    if(epic.isFavorite() == NOT_FAVORITE){
                        epic.setFavorite(IS_FAVORITE);
                        viewModel.updateEpicEnhanced(IS_FAVORITE, epic.getIdentifier());
                        favoriteIcon.setImageResource(R.drawable.star_icon);
                        message = getString(R.string.epic_add_message);
                    } else {
                        epic.setFavorite(NOT_FAVORITE);
                        viewModel.updateEpicEnhanced(NOT_FAVORITE, epic.getIdentifier());
                        favoriteIcon.setImageResource(R.drawable.star_icon_default);
                        message = getString(R.string.epic_remove_message);
                    }
                    Snackbar.make(constraintLayout, message, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.epic_share_title));
                String epicUrl =Utils.buildEpicNaturalImageUrl(epic.getDate(), epic.getImage());
                String message = String.format(getString(R.string.epic_share_message), epicUrl);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, getString(R.string.epic_share_title)));
            }
        });
        setUpUI();
        return rootView;
    }

    private void setUpUI(){
        if(epic != null) {
            if(epic.isFavorite() == IS_FAVORITE){
                favoriteIcon.setImageResource(R.drawable.star_icon);
            } else {
                favoriteIcon.setImageResource(R.drawable.star_icon_default);
            }
            String imageUrl = Utils.buildEpicEnhancedImageUrl(epic.getDate(), epic.getImage());
            GlideApp.with(getActivity())
                    .load(imageUrl)
                    .placeholder(R.drawable.blue_marble_placeholder)
                    .error(R.drawable.blue_marble_error)
                    .into(epicImage);
            String dateString = Utils.getEpicDateFormat(epic.getDate());
            dateAndTime.setText(dateString);
            caption.setText(epic.getCaption());
            String distToEarth = String.format(getString(R.string.epic_distance_KM_formatter),
                    epic.getDistanceEpicToEarth());
            distanceToEarth.setText(distToEarth);
            String distToSun = String.format(getString(R.string.epic_distance_KM_formatter),
                    epic.getDistanceEpicToSun());
            distanceToSun.setText(distToSun);
            String sunToEarthDist = String.format(getString(R.string.epic_distance_KM_formatter),
                    epic.getDistanceSunToEarth());
            sunToEarth.setText(sunToEarthDist);
            centroidLatitude.setText(String.valueOf(epic.getCentroid().getLat()));
            centroidLongitude.setText(String.valueOf(epic.getCentroid().getLon()));
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_EPIC, epic);
    }





}
