package com.aleperf.pathfinder.copernicana.injection;

import android.app.Application;

import com.aleperf.pathfinder.copernicana.apod.ApodDetailFragment;
import com.aleperf.pathfinder.copernicana.apod.ApodSummaryFragment;
import com.aleperf.pathfinder.copernicana.intro.MainActivity;
import com.aleperf.pathfinder.copernicana.intro.SummaryFragment;

import dagger.Component;

@CopernicanaApplicationScope
@Component(modules ={ApisServiceModule.class, ApplicationModule.class, AstroDatabaseModule.class})
public interface CopernicanaApplicationComponent {

    void inject(SummaryFragment summaryFragment);
    void inject(ApodSummaryFragment apodSummaryFragment);
    void inject(ApodDetailFragment apodDetailFragment);
    void inject(MainActivity mainActivity);

    Application application();
}
