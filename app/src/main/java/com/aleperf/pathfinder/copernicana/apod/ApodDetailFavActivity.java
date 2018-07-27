package com.aleperf.pathfinder.copernicana.apod;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodDetailFavActivity extends AppCompatActivity {

    private final static String APOD_EXTRA_DETAIL_FAV = "apod extra detail fav";
    @BindView(R.id.toolbar_apod_detail_fav)
    Toolbar toolbar;
    Apod apod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_detail_fav);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            apod = getIntent().getExtras().getParcelable(APOD_EXTRA_DETAIL_FAV);
        } else {
            apod = savedInstanceState.getParcelable(APOD_EXTRA_DETAIL_FAV);
        }
        if (apod != null) {
            getSupportActionBar().setTitle(apod.getTitle());
            ApodDetailFragment apodDetailFragment = ApodDetailFragment.getInstance(apod);
            fragmentManager.beginTransaction().
                    replace(R.id.apod_detail_fav_fragment_container, apodDetailFragment).commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(APOD_EXTRA_DETAIL_FAV, apod);
    }
}
