package com.aleperf.pathfinder.copernicana.iss;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.aleperf.pathfinder.copernicana.utilities.SummaryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IssActivity extends AppCompatActivity implements SummaryAdapter.SectionSelector,
AstronautsAdapter.AstronautSelector{

    @BindView(R.id.toolbar_iss)
    Toolbar toolbar;
    private boolean isDualPane;
    private int sectionSelectedIssSection = 1;
    private final static String SECTION_SELECTED_ISS = "section selected iss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iss);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.iss_title));
        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);
        if (savedInstanceState != null && isDualPane) {
            sectionSelectedIssSection = savedInstanceState.getInt(SECTION_SELECTED_ISS);
            selectSection(sectionSelectedIssSection);
        } else {
            if(isDualPane){
                selectSection(1);
            }
        }

    }

    @Override
    public void selectSection(int position) {
        if (!isDualPane) {
            switch (position) {
                case 1:
                    Intent issPositionIntent = new Intent(this, IssPositionActivity.class);
                    startActivity(issPositionIntent);
                    break;
                default:
                    Intent astronautsIntent = new Intent(this, AstronautsActivity.class);
                    startActivity(astronautsIntent);

            }
        } else {
            sectionSelectedIssSection = position;
            FragmentManager fragmentManager = getSupportFragmentManager();
            String tag = SECTION_SELECTED_ISS + sectionSelectedIssSection;
            switch (position) {
                case 1:
                    IssPositionFragment issPositionFragment = (IssPositionFragment) fragmentManager.findFragmentByTag(tag);
                    if (issPositionFragment == null) {
                        issPositionFragment = new IssPositionFragment();
                    }
                    fragmentManager.beginTransaction().replace(R.id.iss_section_detail_container, issPositionFragment, tag).commit();
                    break;
                default:
                    AstronautsFragment astronautsFragment = (AstronautsFragment) fragmentManager.findFragmentByTag(tag);
                    if (astronautsFragment == null) {
                        astronautsFragment = AstronautsFragment.newInstance();
                    }
                    fragmentManager.beginTransaction().replace(R.id.iss_section_detail_container, astronautsFragment, tag).commit();

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SECTION_SELECTED_ISS, sectionSelectedIssSection);
    }

    @Override
    public void selectAstronaut(Astronaut astronaut) {
        if(isDualPane){
            FragmentManager fragmentManager = getSupportFragmentManager();
            AstronautDetailDialogFragment dialogFragment = AstronautDetailDialogFragment.getInstance(astronaut);
            dialogFragment.show(fragmentManager, AstronautDetailDialogFragment.class.getSimpleName());
        }
    }
}
