package com.aleperf.pathfinder.copernicana.injection;

import com.aleperf.pathfinder.copernicana.intro.SummaryFragment;

import dagger.Component;

@CopernicanaApplicationScope
@Component(modules ={ApisServiceModule.class, ApplicationModule.class})
public interface CopernicanaApplicationComponent {

    void inject(SummaryFragment summaryFragment);
}
