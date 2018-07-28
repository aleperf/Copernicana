package com.aleperf.pathfinder.copernicana.apod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodDisplayAllActivity extends AppCompatActivity implements ApodAdapter.ApodDetailLauncher {

    private final String APOD_EXTRA_DETAIL_ALL = "apod extra detail all";

    @BindView(R.id.toolbar_apod_display_all)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_display_all);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.apod_display_all_title));
    }

    @Override
    public void showApodDetail(Apod apod) {
        Intent intent = new Intent(this, ApodDetailAllActivity.class);
        intent.putExtra(APOD_EXTRA_DETAIL_ALL, apod);
        startActivity(intent);
        }
}
