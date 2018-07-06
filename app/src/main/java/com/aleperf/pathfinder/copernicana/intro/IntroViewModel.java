package com.aleperf.pathfinder.copernicana.intro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Astronaut;

import java.util.List;

import javax.inject.Inject;

public class IntroViewModel extends ViewModel {

    private final static String TAG = IntroViewModel.class.getSimpleName();
    private AstroRepository astroRepository;
    private LiveData<Apod> apod;


    @Inject
    public IntroViewModel(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;
        astroRepository.initializeRepository();

    }


    public LiveData<Apod> getApod() {
       if(apod == null){
           apod = astroRepository.getLastApod();
           return apod;
       }

       return apod;
    }

}
