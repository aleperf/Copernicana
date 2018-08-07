package com.aleperf.pathfinder.copernicana.database;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.widget.CopernicanaAppWidgetProvider;
import com.firebase.jobdispatcher.JobService;

import javax.inject.Inject;

public class UpdateService extends JobService {

    @Inject
    AstroRepository astroRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        ((CopernicanaApplication) this.getApplication())
                .getCopernicanaApplicationComponent().inject(this);

    }


    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {
        astroRepository.updateRepository();
        Log.d("uffa", "sto updatando la repository");
        return false;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {

        return false;
    }


}
