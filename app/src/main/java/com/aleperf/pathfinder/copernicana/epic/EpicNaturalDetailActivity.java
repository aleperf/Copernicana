package com.aleperf.pathfinder.copernicana.epic;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EpicNaturalDetailActivity extends AppCompatActivity {

    private final static String EXTRA_EPIC_NATURAL = "extra epic natural";
    private final static String NATURAL_POSTFIX_TAG = "natural";
    @BindView(R.id.toolbar_epic_natural_detail)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic_natural_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.epic_detail_natural_toolbar_title));
        Epic epic = getIntent().getExtras().getParcelable(EXTRA_EPIC_NATURAL);
        if (epic != null) {
            String FRAGMENT_TAG = String.valueOf(epic.getIdentifier() + NATURAL_POSTFIX_TAG);
            FragmentManager fragmentManager = getSupportFragmentManager();
            EpicNaturalDetailFragment fragment = (EpicNaturalDetailFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if(fragment == null){
             fragment = EpicNaturalDetailFragment.getInstance(epic);}

            fragmentManager.beginTransaction().
                    replace(R.id.epic_natural_detail_container, fragment, FRAGMENT_TAG).commit();

        }


    }

}