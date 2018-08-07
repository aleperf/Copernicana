package com.aleperf.pathfinder.copernicana.epic;

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

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.GlideApp;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.utilities.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class EpicNaturalSearchDetailDialogFragment extends DialogFragment {

    private final static String EPIC_NATURAL_FROM_SEARCH = "Epic natural from search";
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
    EpicNaturalSearchDetailViewModel viewModel;

    @Inject
    ViewModelProvider.Factory factory;

    public EpicNaturalSearchDetailDialogFragment () {
    }


    public static  EpicNaturalSearchDetailDialogFragment getInstance(Epic epic) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EPIC_NATURAL_FROM_SEARCH, epic);
        EpicNaturalSearchDetailDialogFragment fragment = new  EpicNaturalSearchDetailDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) this.getActivity().getApplication())
                .getCopernicanaApplicationComponent().inject(this);
        if (savedInstanceState == null) {
            epic = getArguments().getParcelable(EPIC_NATURAL_FROM_SEARCH);
        } else {
            epic = savedInstanceState.getParcelable(EPIC_NATURAL_FROM_SEARCH);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_epic_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        viewModel = ViewModelProviders.of(this, factory).get(EpicNaturalSearchDetailViewModel.class);
        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewModel != null){
                    String message;
                    if(epic.isFavorite() == NOT_FAVORITE){
                        epic.setFavorite(IS_FAVORITE);
                        viewModel.insertEpicNaturalFromSearch(epic);
                        favoriteIcon.setImageResource(R.drawable.star_icon);
                        message = getString(R.string.epic_add_message);
                    } else {
                        epic.setFavorite(NOT_FAVORITE);
                        viewModel.insertEpicNaturalFromSearch(epic);
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
            String imageUrl = Utils.buildEpicNaturalImageUrl(epic.getDate(), epic.getImage());
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
    public void onResume() {
        super.onResume();
        Resources res = getResources();
        int width = res.getDimensionPixelSize(R.dimen.dialog_fragment_width_epic);
        int height = res.getDimensionPixelSize(R.dimen.dialog_fragment_height_epic);
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
        outState.putParcelable(EPIC_NATURAL_FROM_SEARCH, epic);
    }
}
