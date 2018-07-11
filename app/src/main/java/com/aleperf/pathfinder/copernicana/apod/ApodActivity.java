package com.aleperf.pathfinder.copernicana.apod;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aleperf.pathfinder.copernicana.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodActivity extends AppCompatActivity {

    @BindView(R.id.apod_view_pager)
    ViewPager apodViewPager;
    @BindView(R.id.apod_tabs)
    TabLayout apodTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        ButterKnife.bind(this);
        ApodPagerAdapter apodPagerAdapter = new ApodPagerAdapter(getSupportFragmentManager(),this);
        apodViewPager.setAdapter(apodPagerAdapter);
        apodTabLayout.setupWithViewPager(apodViewPager);
    }

    public static class ApodPagerAdapter extends FragmentPagerAdapter{

        private static int NUM_OF_PAGES = 4;
        private Context context;

        public ApodPagerAdapter(FragmentManager fragmentManager, Context context){
            super(fragmentManager);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return ApodDetailFragment.getInstance(ApodDetailFragment.APOD_DEFAULT_DATE);
            } else {
                return new ApodSummaryFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_OF_PAGES;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Daily Pic";
                case 1:
                    return "Search";
                case 2:
                    return "Favorites";
                case 3: return "All Pics";

                default:
                    return "Ciao";
            }
        }
    }
}
