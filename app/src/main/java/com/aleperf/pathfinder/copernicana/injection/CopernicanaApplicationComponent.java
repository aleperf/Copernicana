package com.aleperf.pathfinder.copernicana.injection;

import android.app.Application;

import com.aleperf.pathfinder.copernicana.apod.ApodDetailFragment;
import com.aleperf.pathfinder.copernicana.apod.ApodDisplayAllFragment;
import com.aleperf.pathfinder.copernicana.apod.ApodFavoritesFragment;
import com.aleperf.pathfinder.copernicana.apod.ApodSearchFragment;
import com.aleperf.pathfinder.copernicana.apod.ApodSummaryFragment;
import com.aleperf.pathfinder.copernicana.epic.EpicActivity;
import com.aleperf.pathfinder.copernicana.intro.IntroActivity;
import com.aleperf.pathfinder.copernicana.intro.SummaryFragment;
import com.aleperf.pathfinder.copernicana.iss.IssSummaryFragment;
import com.aleperf.pathfinder.copernicana.mars.MarsActivity;

import dagger.Component;

@CopernicanaApplicationScope
@Component(modules ={ApisServiceModule.class, ApplicationModule.class, AstroDatabaseModule.class})
public interface CopernicanaApplicationComponent {

    void inject(SummaryFragment summaryFragment);
    void inject(ApodSummaryFragment apodSummaryFragment);
    void inject(ApodDetailFragment apodDetailFragment);
    void inject(ApodSearchFragment apodSearchFragment);
    void inject(ApodFavoritesFragment apodFavoritesFragment);
    void inject(ApodDisplayAllFragment apodDisplayAllFragment);
    void inject(IntroActivity mainActivity);
    void inject(MarsActivity marsActivity);


    Application application();
}
