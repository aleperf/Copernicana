package com.aleperf.pathfinder.copernicana.iss;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Astronaut;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AstronautDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_astronaut_detail)
    Toolbar toolbar;

    private final static String EXTRA_ASTRONAUT = "extra astronaut copernicana";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astronaut_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.astronauts_detail_toolbar));
        Astronaut astronaut = getIntent().getParcelableExtra(EXTRA_ASTRONAUT);
        String tag = astronaut.getId() + astronaut.getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        AstronautDetailFragment fragment = (AstronautDetailFragment) fragmentManager.findFragmentByTag(tag);
        if(fragment == null){
            fragment = AstronautDetailFragment.getInstance(astronaut);
        }
        fragmentManager.beginTransaction().replace(R.id.astronaut_fragment_container, fragment, tag).commit();


    }
}
