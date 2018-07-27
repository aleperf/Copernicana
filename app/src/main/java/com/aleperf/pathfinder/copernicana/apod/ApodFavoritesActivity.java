package com.aleperf.pathfinder.copernicana.apod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodFavoritesActivity extends AppCompatActivity implements ApodAdapter.ApodDetailLauncher {

    private final static String APOD_EXTRA_DETAIL_FAV = "apod extra detail fav";
    @BindView(R.id.toolbar_apod_favorites)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_favorites);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.apod_favorites_toolbar_title));
    }

    @Override
    public void showApodDetail(Apod apod) {
        Intent intent = new Intent(this, ApodDetailFavActivity.class);
        intent.putExtra(APOD_EXTRA_DETAIL_FAV, apod);
        startActivity(intent);
    }
}
