package com.aleperf.pathfinder.copernicana.iss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.aleperf.pathfinder.copernicana.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AstronautsActivity extends AppCompatActivity implements AstronautsAdapter.AstronautSelector{

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
    public void selectAstronaut(int id) {
        Toast.makeText(this, "clicked name " + id, Toast.LENGTH_SHORT).show();
    }
}
