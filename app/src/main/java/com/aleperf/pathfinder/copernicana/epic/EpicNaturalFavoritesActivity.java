package com.aleperf.pathfinder.copernicana.epic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpicNaturalFavoritesActivity extends AppCompatActivity implements EpicAdapter.EpicNaturalSelector {
    private final static String EXTRA_EPIC_NATURAL = "extra epic natural";
    @BindView(R.id.toolbar_epic_natural_fav)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic_natural_favorites);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.epic_natural_favorites_label));
    }

    @Override
    public void selectEpic(Epic epic) {
        Intent intent = new Intent(this, EpicNaturalDetailActivity.class);
        intent.putExtra(EXTRA_EPIC_NATURAL, epic);
        startActivity(intent);

    }
}
