package com.aleperf.pathfinder.copernicana.injection;

import android.app.Application;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final CopernicanaApplication application;

    public ApplicationModule(CopernicanaApplication application){
        this.application = application;
    }

    @Provides
    @CopernicanaApplicationScope
    CopernicanaApplication provideBakingApplication(){
        return application;
    }

    @Provides
    @CopernicanaApplicationScope
    Application provideApplication(){
        return application;
    }
}
