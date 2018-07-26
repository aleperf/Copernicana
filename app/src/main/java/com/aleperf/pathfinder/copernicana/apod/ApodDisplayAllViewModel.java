package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import javax.inject.Inject;

public class ApodDisplayAllViewModel extends ViewModel {

    private MutableLiveData<Apod> allApods;


    AstroRepository astroRepository;

            @Inject
            public ApodDisplayAllViewModel(AstroRepository astroRepository){
                this.astroRepository = astroRepository;
            }
}
