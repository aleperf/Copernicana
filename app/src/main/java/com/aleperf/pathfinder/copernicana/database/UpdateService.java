package com.aleperf.pathfinder.copernicana.database;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
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

        return false;
    }


}
