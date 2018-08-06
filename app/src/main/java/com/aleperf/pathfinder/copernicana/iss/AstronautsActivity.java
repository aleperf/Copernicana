package com.aleperf.pathfinder.copernicana.iss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Astronaut;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AstronautsActivity extends AppCompatActivity implements AstronautsAdapter.AstronautSelector {

    private final static String EXTRA_ASTRONAUT = "extra astronaut copernicana";

    @BindView(R.id.toolbar_astronauts)
    Toolbar toolbar;
    private boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astronauts);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.iss_astronauts_label));
        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);
    }

    @Override
    public void selectAstronaut(Astronaut astronaut) {
        if (!isDualPane) {
            Intent intent = new Intent(this, AstronautDetailActivity.class);
            intent.putExtra(EXTRA_ASTRONAUT, astronaut);
            startActivity(intent);
        }
    }
}
