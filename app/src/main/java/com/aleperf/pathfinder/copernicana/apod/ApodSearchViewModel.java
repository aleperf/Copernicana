package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.network.ApisService;

import javax.inject.Inject;

public class ApodSearchViewModel extends ViewModel {

   private AstroRepository astroRepository;
   private MutableLiveData<Apod> apodSearched;




    @Inject
    public ApodSearchViewModel(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;
        apodSearched = new MutableLiveData<>();
    }

    public MutableLiveData<Apod> getApodSearched() {
        return apodSearched;
    }

    public void searchApodForDate(String date){
        astroRepository.searchApodForDate(date, apodSearched);
    }


}
