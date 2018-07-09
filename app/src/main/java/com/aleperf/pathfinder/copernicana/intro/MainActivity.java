package com.aleperf.pathfinder.copernicana.intro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.apod.ApodActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements SummaryFragment.SectionSelector {


    @BindView(R.id.toolbar_intro)
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

    }


    @Override
    public void selectSection(int position) {
        Toast.makeText(this, "clicked section at position " + position, Toast.LENGTH_SHORT).show();
    }
}
