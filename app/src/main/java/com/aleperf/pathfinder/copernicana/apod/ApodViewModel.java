package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import javax.inject.Inject;

public class ApodViewModel extends ViewModel {

    AstroRepository astroRepository;
    LiveData<Apod> lastApod;

    @Inject
    public ApodViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
    }

    public LiveData<Apod> getLastApod(){
        if(lastApod == null){
            lastApod = astroRepository.getLastApod();
        }
        return lastApod;
    }


}
