package com.aleperf.pathfinder.copernicana.epic;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpicEnhancedDetailActivity extends AppCompatActivity {

    private final static String EXTRA_EPIC_ENHANCED = "extra epic enhanced";
    private final String ENHANCED_POSTFIX_TAG = "enhanced";
    @BindView(R.id.toolbar_epic_enhanced_detail)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic_enhanced_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.epic_detail_natural_toolbar_title));
        EpicEnhanced epicEnhanced = getIntent().getExtras().getParcelable(EXTRA_EPIC_ENHANCED);
        if (epicEnhanced != null) {
            String FRAGMENT_TAG = String.valueOf(epicEnhanced.getIdentifier()) + ENHANCED_POSTFIX_TAG;
            FragmentManager fragmentManager = getSupportFragmentManager();
            EpicEnhancedDetailFragment fragment = (EpicEnhancedDetailFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if (fragment == null) {
                fragment = EpicEnhancedDetailFragment.getInstance(epicEnhanced);
                fragmentManager.beginTransaction().
                        replace(R.id.epic_enhanced_detail_fragment_container, fragment, FRAGMENT_TAG).commit();

            }
        }
    }

}
