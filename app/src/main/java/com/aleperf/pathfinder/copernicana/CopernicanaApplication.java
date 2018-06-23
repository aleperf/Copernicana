package com.aleperf.pathfinder.copernicana;

import android.app.Activity;
import android.app.Application;


public class CopernicanaApplication extends Application {

    public static CopernicanaApplication CopernicanaApplication(Activity activity){

        return (CopernicanaApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
