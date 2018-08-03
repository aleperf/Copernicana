package com.aleperf.pathfinder.copernicana.database;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

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
        return false;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        updateWidget();
        return false;
    }


    private void updateWidget(){
        Intent intent = new Intent(this.getApplicationContext(), CopernicanaAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(this.getApplicationContext()).getAppWidgetIds(new ComponentName(getApplication(), CopernicanaAppWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }
}
