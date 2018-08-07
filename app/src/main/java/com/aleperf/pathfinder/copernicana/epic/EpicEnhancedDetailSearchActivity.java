package com.aleperf.pathfinder.copernicana.epic;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpicEnhancedDetailSearchActivity extends AppCompatActivity {

    private final static String EPIC_ENHANCED_FROM_SEARCH = "Epice enhancd from search";

    @BindView(R.id.toolbar_epic_enhanced_det_search)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic_enhanced_detail_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.epic_detail_natural_toolbar_title));
        EpicEnhanced epicEnhanced = getIntent().getExtras().getParcelable(EPIC_ENHANCED_FROM_SEARCH);
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentTag = String.valueOf(epicEnhanced.getIdentifier()) + EPIC_ENHANCED_FROM_SEARCH;
        EpicEnhancedDetailSearchFragment fragment = (EpicEnhancedDetailSearchFragment) fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = EpicEnhancedDetailSearchFragment.getInstance(epicEnhanced);
        }
        fragmentManager.beginTransaction().replace(R.id.epic_enhanced_search_detail_container,
                fragment, fragmentTag).commit();
    }
}
