package com.aleperf.pathfinder.copernicana.epic;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;
import com.aleperf.pathfinder.copernicana.utilities.SummaryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EpicActivity extends AppCompatActivity implements SummaryAdapter.SectionSelector,
        EpicAdapter.EpicNaturalSelector, EpicEnhancedAdapter.EpicEnhancedSelector,
        EpicAdapter.EpicNaturalSearchSelector, EpicEnhancedAdapter.EpicEnhancedSearchSelector {

    @BindView(R.id.toolbar_epic)
    Toolbar toolbar;
    private boolean isDualPane;
    private int sectionSelectedEpic = 1;
    private final static String SECTION_SELECTED_EPIC = "section selected epic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.epic_title));
        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);
        if (savedInstanceState != null) {
            sectionSelectedEpic = savedInstanceState.getInt(SECTION_SELECTED_EPIC);
            if (isDualPane) {
                selectSection(sectionSelectedEpic);
            }
        } else {
            if (isDualPane) {
                selectSection(1);
            }
        }

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
                    Intent enhancedIntent = new Intent(this, EpicEnhancedActivity.class);
                    startActivity(enhancedIntent);
                    break;
                case 3:
                    Intent naturalFavoritesIntent = new Intent(this, EpicNaturalFavoritesActivity.class);
                    startActivity(naturalFavoritesIntent);
                    break;
                case 4:
                    Intent enhancedFavoritesIntent = new Intent(this, EpicEnhancedFavActivity.class);
                    startActivity(enhancedFavoritesIntent);
                    break;

                default:
                    Intent searchIntent = new Intent(this, EpicSearchActivity.class);
                    startActivity(searchIntent);
                    break;
            }
        } else {
            sectionSelectedEpic = position;
            FragmentManager fragmentManager = getSupportFragmentManager();
            String tag = SECTION_SELECTED_EPIC + sectionSelectedEpic;
            switch (position) {
                case 1:
                    EpicNaturalFragment epicNaturalFragment = (EpicNaturalFragment) fragmentManager.findFragmentByTag(tag);
                    if (epicNaturalFragment == null) {
                        epicNaturalFragment = new EpicNaturalFragment();
                    }
                    fragmentManager.beginTransaction().replace(R.id.epic_section_detail_container,
                            epicNaturalFragment, tag).commit();
                    break;
                case 2:
                    EpicEnhancedFragment epicEnhancedFragment = (EpicEnhancedFragment) fragmentManager.findFragmentByTag(tag);
                    if (epicEnhancedFragment == null) {
                        epicEnhancedFragment = new EpicEnhancedFragment();
                    }
                    fragmentManager.beginTransaction().replace(R.id.epic_section_detail_container,
                            epicEnhancedFragment, tag).commit();
                    break;
                case 3:
                    EpicNaturalFavFragment epicNaturalFavFragment = (EpicNaturalFavFragment) fragmentManager.findFragmentByTag(tag);
                    if (epicNaturalFavFragment == null) {
                        epicNaturalFavFragment = new EpicNaturalFavFragment();
                    }
                    fragmentManager.beginTransaction().replace(R.id.epic_section_detail_container,
                            epicNaturalFavFragment, tag).commit();
                    break;
                case 4:
                    EpicEnhancedFavFragment epicEnhancedFavFragment = (EpicEnhancedFavFragment) fragmentManager.findFragmentByTag(tag);
                    if (epicEnhancedFavFragment == null) {
                        epicEnhancedFavFragment = new EpicEnhancedFavFragment();
                    }
                    fragmentManager.beginTransaction().replace(R.id.epic_section_detail_container,
                            epicEnhancedFavFragment, tag).commit();
                    break;
                default:
                    EpicSearchFragment epicSearchFragment = (EpicSearchFragment) fragmentManager.findFragmentByTag(tag);
                    if (epicSearchFragment == null) {
                        epicSearchFragment = new EpicSearchFragment();
                    }
                    fragmentManager.beginTransaction().replace(R.id.epic_section_detail_container,
                            epicSearchFragment, tag).commit();

            }
        }
    }


    @Override
    public void selectEpic(Epic epic) {
        if (isDualPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            EpicNaturalDetailDialogFragment detailDialogFragment = EpicNaturalDetailDialogFragment
                    .getInstance(epic);
            detailDialogFragment.show(fragmentManager, EpicNaturalDetailDialogFragment.class.getSimpleName());
        }

    }


    @Override
    public void selectEpicEnhanced(EpicEnhanced epicEnhanced) {
        if (isDualPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            EpicEnhancedDetailDialogFragment detailDialogFragment = EpicEnhancedDetailDialogFragment
                    .getInstance(epicEnhanced);
            detailDialogFragment.show(fragmentManager, EpicNaturalDetailDialogFragment.class.getSimpleName());
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SECTION_SELECTED_EPIC, sectionSelectedEpic);
    }

    @Override
    public void selectEpicFromSearch(Epic epic) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        EpicNaturalSearchDetailDialogFragment dialogFragment =
                EpicNaturalSearchDetailDialogFragment.getInstance(epic);
        dialogFragment.show(fragmentManager, EpicNaturalSearchDetailDialogFragment.class.getSimpleName());
    }


    @Override
    public void selectEnhancedEpicFromSearch(EpicEnhanced epicEnhanced) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        EpicEnhancedSearchDetailDialogFragment dialogFragment =
                EpicEnhancedSearchDetailDialogFragment.getInstance(epicEnhanced);
        dialogFragment.show(fragmentManager, EpicEnhancedSearchDetailDialogFragment.class.getSimpleName());

    }
}
