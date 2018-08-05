package com.aleperf.pathfinder.copernicana.epic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpicSearchActivity extends AppCompatActivity implements  EpicAdapter.EpicNaturalSearchSelector,
EpicEnhancedAdapter.EpicEnhancedSearchSelector{

    private final static String EPIC_NATURAL_FROM_SEARCH = "Epic natural from search";
    private final static String EPIC_ENHANCED_FROM_SEARCH = "Epice enhancd from search";

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

    @Override
    public void selectEpicFromSearch(Epic epic) {
        Intent intent = new Intent(this, EpicNaturalSearchDetailActivity.class);
        intent.putExtra(EPIC_NATURAL_FROM_SEARCH, epic);
        startActivity(intent);

    }

    @Override
    public void selectEnhancedEpicFromSearch(EpicEnhanced epicEnhanced) {

        Intent intent = new Intent(this, EpicEnhancedDetailSearchActivity.class);
        intent.putExtra(EPIC_ENHANCED_FROM_SEARCH, epicEnhanced);
        startActivity(intent);

    }
}
