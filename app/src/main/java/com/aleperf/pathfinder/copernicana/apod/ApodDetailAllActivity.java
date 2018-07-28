package com.aleperf.pathfinder.copernicana.apod;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodDetailAllActivity extends AppCompatActivity {

    private final String APOD_EXTRA_DETAIL_ALL = "apod extra detail all";

    @BindView(R.id.toolbar_apod_detail_all)
    Toolbar toolbar;
    Apod apod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_detail_all);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            apod = getIntent().getExtras().getParcelable(APOD_EXTRA_DETAIL_ALL);
        } else {
            apod = savedInstanceState.getParcelable(APOD_EXTRA_DETAIL_ALL);
        }
        if (apod != null) {
            getSupportActionBar().setTitle(apod.getTitle());
            ApodDetailFragment apodDetailFragment = ApodDetailFragment.getInstance(apod);
            fragmentManager.beginTransaction().
                    replace(R.id.apod_detail_all_fragment_container, apodDetailFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(APOD_EXTRA_DETAIL_ALL, apod);
    }
}
