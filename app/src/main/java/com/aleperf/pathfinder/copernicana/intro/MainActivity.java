package com.aleperf.pathfinder.copernicana.intro;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.apod.ApodActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.multibindings.IntKey;


public class MainActivity extends AppCompatActivity implements SummaryFragment.SectionSelector {


    @BindView(R.id.toolbar_intro)
    Toolbar toolbar;
    @Inject
    ViewModelProvider.Factory factory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ((CopernicanaApplication) this.getApplication()).getCopernicanaApplicationComponent().inject(this);
        ViewModel viewModel = ViewModelProviders.of(this, factory).get(IntroViewModel.class);

    }


    @Override
    public void selectSection(int position) {
        if (position == 0) {
            Intent intent = new Intent(this, ApodActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "clicked section at position " + position, Toast.LENGTH_SHORT).show();
        }
    }
}
