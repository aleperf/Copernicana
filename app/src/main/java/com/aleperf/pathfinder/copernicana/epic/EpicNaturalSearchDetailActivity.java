package com.aleperf.pathfinder.copernicana.epic;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpicNaturalSearchDetailActivity extends AppCompatActivity {
    private final static String EPIC_NATURAL_FROM_SEARCH = "Epic natural from search";

    @BindView(R.id.toolbar_epic_natural_det_search)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic_natural_search_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.epic_detail_natural_toolbar_title));
        Epic epic = getIntent().getExtras().getParcelable(EPIC_NATURAL_FROM_SEARCH);
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentTag = String.valueOf(epic.getIdentifier()) + EPIC_NATURAL_FROM_SEARCH;
        EpicNaturalDetailSearchFragment fragment = (EpicNaturalDetailSearchFragment) fragmentManager.findFragmentByTag(fragmentTag);
        if(fragment == null){
            fragment = EpicNaturalDetailSearchFragment.getInstance(epic);
        }
        fragmentManager.beginTransaction().replace(R.id.epic_natural_search_detail_container,
                fragment, fragmentTag).commit();
    }
}
