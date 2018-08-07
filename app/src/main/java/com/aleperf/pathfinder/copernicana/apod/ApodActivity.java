package com.aleperf.pathfinder.copernicana.apod;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.intro.IntroActivity;
import com.aleperf.pathfinder.copernicana.model.Apod;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodActivity extends AppCompatActivity implements ApodSummaryAdapter.ApodSectionSelector,
        ApodSummaryFragment.ApodSetter, ApodAdapter.ApodDetailLauncher {

    @BindView(R.id.toolbar_apod)
    Toolbar toolbar;
    private boolean isDualPane;
    private static final String APOD_EXTRA = "apod extra";
    private final static String SECTION_SELECTED = "apod section selected ";
    private int sectionSelected = 0;
    private Apod latestApod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.apod_card_rv_title));
        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);
        if (savedInstanceState != null) {
            sectionSelected = savedInstanceState.getInt(SECTION_SELECTED);
            if (isDualPane) {
                if (sectionSelected != 0) {
                    selectSection(sectionSelected);
                }
            }
        }


    }


    @Override
    public void selectSection(int position) {
        if (!isDualPane) {
            switch (position) {
                case 1:
                    Intent searchIntent = new Intent(this, ApodSearchActivity.class);
                    startActivity(searchIntent);
                    break;
                case 2:
                    Intent favoritesIntent = new Intent(this, ApodFavoritesActivity.class);
                    startActivity(favoritesIntent);
                    break;
                default:
                    Intent displayallApodIntent = new Intent(this, ApodDisplayAllActivity.class);
                    startActivity(displayallApodIntent);
            }
        } else {
            sectionSelected = position;
            FragmentManager fragmentManager = getSupportFragmentManager();
            String tag = SECTION_SELECTED + sectionSelected;
            switch (position) {
                case 1:
                    ApodSearchFragment searchFragment = (ApodSearchFragment) fragmentManager.findFragmentByTag(tag);
                    if (searchFragment == null) {
                        searchFragment = new ApodSearchFragment();
                    }
                    fragmentManager.beginTransaction().replace(R.id.apod_section_detail_container,
                            searchFragment, tag).commit();
                    break;
                case 2:
                    ApodFavoritesFragment favoritesFragment = (ApodFavoritesFragment) fragmentManager.findFragmentByTag(tag);
                    if (favoritesFragment == null) {
                        favoritesFragment = new ApodFavoritesFragment();
                    }
                    fragmentManager.beginTransaction().replace(R.id.apod_section_detail_container,
                            favoritesFragment, tag).commit();
                    break;
                default:
                    ApodDisplayAllFragment displayAllFragment = (ApodDisplayAllFragment) fragmentManager.findFragmentByTag(tag);
                    if (displayAllFragment == null) {
                        displayAllFragment = new ApodDisplayAllFragment();
                    }
                    fragmentManager.beginTransaction().replace(R.id.apod_section_detail_container,
                            displayAllFragment, tag).commit();

            }
        }

    }

    @Override
    public void selectApodSection(Apod apod) {
        if (!isDualPane) {
            Intent intent = new Intent(this, ApodDetailActivity.class);
            intent.putExtra(APOD_EXTRA, apod);
            startActivity(intent);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            String tag = SECTION_SELECTED + sectionSelected;
            ApodDetailFragment fragment = (ApodDetailFragment) fragmentManager.findFragmentByTag(tag);
            if (fragment == null) {
                fragment = ApodDetailFragment.getInstance(apod);
            }
            fragmentManager.beginTransaction().replace(R.id.apod_section_detail_container, fragment, tag).commit();

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToParent();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navigateToParent();
        }
        return true;
    }


    private void navigateToParent() {
        Intent navigateUpToParentIntent = new Intent(this, IntroActivity.class);
        navigateUpToParentIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (getParent() != null) {
            NavUtils.navigateUpTo(this, navigateUpToParentIntent);
        } else {
            startActivity(navigateUpToParentIntent);
        }
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SECTION_SELECTED, sectionSelected);
    }

    @Override
    public void setApod(Apod apod) {
        latestApod = apod;
        if (sectionSelected == 0 && isDualPane) {
            selectApodSection(latestApod);
        }
    }

    @Override
    public void showApodDetail(Apod apod) {
        if (isDualPane) {
            Log.d("uffa", "sono in showApodDetail");
            //launch dialog fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            ApodDetailDialogFragment apodDetailDialogFragment = ApodDetailDialogFragment.newInstance(apod);
            apodDetailDialogFragment.show(fragmentManager, ApodDetailDialogFragment.class.getSimpleName());

        }
    }
}
