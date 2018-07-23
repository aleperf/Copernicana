package com.aleperf.pathfinder.copernicana.apod;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;


import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodActivity extends AppCompatActivity implements ApodSummaryAdapter.ApodSectionSelector {

    @BindView(R.id.toolbar_apod)
    Toolbar toolbar;
    private boolean isDualPane;
    private static final String APOD_EXTRA_DATE = "apod extra date";
    private static final String APOD_EXTRA_TITLE = "apod extra title";
    private static final String APOD_EXTRA = "apod extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.apod_card_rv_title));
        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);
    }


    @Override
    public void selectSection(int position) {
        if (!isDualPane) {
            switch (position) {
                case 1:
                    Intent searchIntent = new Intent(this, ApodSearchActivity.class);
                    startActivity(searchIntent);
                    break;
                default:
                    Toast.makeText(this, "clicked section: " + position, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void selectApodSection(Apod apod) {
        if (!isDualPane) {
            Intent intent = new Intent(this, ApodDetailActivity.class);
            intent.putExtra(APOD_EXTRA, apod);
            startActivity(intent);
        }

    }
}
