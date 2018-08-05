package com.aleperf.pathfinder.copernicana.epic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpicSearchActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_epic_search)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.epic_search_toolbar_title));
    }
}
