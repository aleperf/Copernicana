package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;

import javax.inject.Inject;

public class ApodViewModel extends ViewModel {

    AstroRepository astroRepository;

    @Inject
    public ApodViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
    }
}
