package com.aleperf.pathfinder.copernicana.epic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.aleperf.pathfinder.copernicana.BuildConfig;
import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;
import com.aleperf.pathfinder.copernicana.network.ApisService;
import com.aleperf.pathfinder.copernicana.utilities.SummaryAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpicActivity extends AppCompatActivity implements SummaryAdapter.SectionSelector,
        EpicAdapter.EpicNaturalSelector, EpicEnhancedAdapter.EpicEnhancedSelector {

    @BindView(R.id.toolbar_epic)
    Toolbar toolbar;
    private boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.epic_title));
        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);

    }


    @Override
    public void selectSection(int position) {
        if (!isDualPane) {
            switch (position) {
                case 1:
                    Intent naturalIntent = new Intent(this, EpicNaturalActivity.class);
                    startActivity(naturalIntent);
                    break;
                case 2:
                    Log.d("uffa", "sono in epicActivity e sto facendo partire EpicEnhancedActivity");
                    Intent enhancedIntent = new Intent(this, EpicEnhancedActivity.class);
                    startActivity(enhancedIntent);
                    break;
                default:
                    Toast.makeText(this, "clicked element " + position, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void selectEpic(Epic epic) {

    }


    @Override
    public void selectEpicEnhanced(EpicEnhanced epicEnhanced) {

    }
}
