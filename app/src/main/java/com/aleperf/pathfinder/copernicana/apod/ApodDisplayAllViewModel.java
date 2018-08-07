package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.List;

import javax.inject.Inject;

public class ApodDisplayAllViewModel extends ViewModel {

    private AstroRepository astroRepository;
    private LiveData<List<Apod>> latestApods;


    @Inject
    public ApodDisplayAllViewModel(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;
        loadAllApods();
    }


    public LiveData<List<Apod>> getAllApods() {

        return latestApods;
    }

    public void loadAllApods() {
        latestApods = astroRepository.getAllApodOrderDesc();
    }
}
