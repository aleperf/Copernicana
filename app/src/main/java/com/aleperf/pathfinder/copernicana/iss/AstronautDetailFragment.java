package com.aleperf.pathfinder.copernicana.iss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.GlideApp;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AstronautDetailFragment extends Fragment {

    private Unbinder unbinder;
    private Astronaut astronaut;
    @BindView(R.id.astronaut_name)
    TextView astronautName;
    @BindView(R.id.astronaut_image)
    ImageView astronautImage;
    @BindView(R.id.mini_bio)
    TextView miniBio;
    @BindView(R.id.craft_name)
    TextView craftName;
    @BindView(R.id.agency_value)
    TextView agency;
    @BindView(R.id.country_name)
    TextView country;
    @BindView(R.id.eva_hours_value)
    TextView evaHours;
    @BindView(R.id.eva_minutes_values)
    TextView evaMinutes;
    @BindView(R.id.wikipedia_icon)
    ImageView wikipediaIcon;

    private FirebaseStorage firebaseStorage;

    private final static String EXTRA_ASTRONAUT = "extra astronaut copernicana";

    public static AstronautDetailFragment getInstance(Astronaut astronaut){
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ASTRONAUT, astronaut);
        AstronautDetailFragment fragment = new AstronautDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public AstronautDetailFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        astronaut = bundle.getParcelable(EXTRA_ASTRONAUT);
        firebaseStorage = FirebaseStorage.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_astronaut_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        if(astronaut != null){
            astronautName.setText(astronaut.getName());
            miniBio.setText(astronaut.getNotes());
            StorageReference storageRef = firebaseStorage.getReference().child(astronaut.getPhoto());
            GlideApp.with(getActivity()).load(storageRef)
                    .error(R.drawable.astronaut_place_holder)
                    .placeholder(R.drawable.astronaut_place_holder)
                    .into(astronautImage);
            astronautName.setText(astronaut.getName());
            evaHours.setText(String.valueOf(astronaut.getEvaHours()));
            evaMinutes.setText(String.valueOf(astronaut.getEvaMinutes()));
            craftName.setText(astronaut.getCraft());
            country.setText(astronaut.getCountry());
            agency.setText(astronaut.getAgency());
            wikipediaIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openPage = new Intent(Intent.ACTION_VIEW);
                    String wikiUrl = astronaut.getWikipedia();
                    openPage.setData(Uri.parse(wikiUrl));
                    startActivity(openPage);
                }
            });
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
