package com.aleperf.pathfinder.copernicana.apod;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodDisplayAllActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_apod_display_all)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_display_all);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.apod_favorites_toolbar_title));
    }
}
