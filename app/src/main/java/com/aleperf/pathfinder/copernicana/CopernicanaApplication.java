package com.aleperf.pathfinder.copernicana;

import android.app.Activity;
import android.app.Application;

import com.aleperf.pathfinder.copernicana.injection.ApisServiceModule;
import com.aleperf.pathfinder.copernicana.injection.ApplicationModule;
import com.aleperf.pathfinder.copernicana.injection.AstroDatabaseModule;
import com.aleperf.pathfinder.copernicana.injection.CopernicanaApplicationComponent;
import com.aleperf.pathfinder.copernicana.injection.DaggerCopernicanaApplicationComponent;


public class CopernicanaApplication extends Application {

    private CopernicanaApplicationComponent component;

    public static CopernicanaApplication get(Activity activity){

        return (CopernicanaApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerCopernicanaApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .astroDatabaseModule(new AstroDatabaseModule(this))
                .apisServiceModule(new ApisServiceModule())
                .build();
    }


    public CopernicanaApplicationComponent getCopernicanaApplicationComponent(){
        return component;
    }

}
