package com.aleperf.pathfinder.copernicana.epic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpicEnhancedFavActivity extends AppCompatActivity implements EpicEnhancedAdapter.EpicEnhancedSelector {

    private final static String EXTRA_EPIC_ENHANCED = "extra epic enhanced";
    @BindView(R.id.toolbar_epic_enhanced_fav)
    Toolbar toolbar;
    private boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic_enhanced_fav);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.epic_enhanced_favorites_label));
        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);
    }

    @Override
    public void selectEpicEnhanced(EpicEnhanced epicEnhanced) {
        Intent intent = new Intent(this, EpicEnhancedDetailActivity.class);
        intent.putExtra(EXTRA_EPIC_ENHANCED, epicEnhanced);
        startActivity(intent);
    }
}
