package com.aleperf.pathfinder.copernicana.apod;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.aleperf.pathfinder.copernicana.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_apod_detail)
    Toolbar toolbar;
    private static final String APOD_EXTRA_DATE = "apod extra";
    private static final String APOD_EXTRA_TITLE = "apod extra title";
    private String date;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            date = getIntent().getStringExtra(APOD_EXTRA_DATE);
            title = getIntent().getStringExtra(APOD_EXTRA_DATE);
        } else {
            date = savedInstanceState.getString(APOD_EXTRA_DATE);
            title = savedInstanceState.getString(APOD_EXTRA_TITLE);
        }
        getSupportActionBar().setTitle(title);
        ApodDetailFragment apodDetailFragment = ApodDetailFragment.getInstance(date);
        fragmentManager.beginTransaction().
                replace(R.id.apod_detail_fragment_container, apodDetailFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(APOD_EXTRA_TITLE, title);
        outState.putString(APOD_EXTRA_DATE, date);
    }
}
