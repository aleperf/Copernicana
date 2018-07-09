package com.aleperf.pathfinder.copernicana.injection;

import android.app.Application;

import com.aleperf.pathfinder.copernicana.apod.ApodDetailFragment;
import com.aleperf.pathfinder.copernicana.intro.SummaryFragment;

import dagger.Component;

@CopernicanaApplicationScope
@Component(modules ={ApisServiceModule.class, ApplicationModule.class, AstroDatabaseModule.class})
public interface CopernicanaApplicationComponent {

    void inject(SummaryFragment summaryFragment);
    void inject(ApodDetailFragment apodDetailFragment);

    Application application();
}
