package com.aleperf.pathfinder.copernicana.iss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.aleperf.pathfinder.copernicana.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IssPositionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_iss_position)
    Toolbar toolbar;
    private boolean isDualPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iss_position);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.iss_position_label));
        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);
    }
}
