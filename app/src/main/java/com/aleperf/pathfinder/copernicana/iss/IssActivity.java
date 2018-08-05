package com.aleperf.pathfinder.copernicana.iss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.utilities.SummaryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IssActivity extends AppCompatActivity implements SummaryAdapter.SectionSelector {

    @BindView(R.id.toolbar_iss)
    Toolbar toolbar;
    private boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iss);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.iss_title));
        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);
    }

    @Override
    public void selectSection(int position) {
        if (!isDualPane) {
            switch (position) {
                case 1:
                    Intent issPositionIntent = new Intent(this, IssPositionActivity.class);
                    startActivity(issPositionIntent);
                    break;
                case 2:
                    Intent issPassageIntent = new Intent(this, IssPassageActivity.class);
                    startActivity(issPassageIntent);
                    break;
                default:
                    Intent astronautsIntent = new Intent(this, AstronautsActivity.class);
                    startActivity(astronautsIntent);

            }
        }
    }
}
