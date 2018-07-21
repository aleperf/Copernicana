package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import javax.inject.Inject;

public class ApodViewModel extends ViewModel {

    private AstroRepository astroRepository;
    private LiveData<Apod> lastApod;
    private LiveData<Apod> apodWithDate;
    private String date;


    @Inject
    public ApodViewModel(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;
    }

    public LiveData<Apod> getLastApod() {
        if (lastApod == null) {
            lastApod = astroRepository.getLastApod();
        }
        return lastApod;
    }

    public LiveData<Apod> getApodWithDate(String date) {

        if (this.date != null && this.date.equals(date)) {
            if (apodWithDate != null) {
                return apodWithDate;
            }
        }
        this.date = date;
        apodWithDate = astroRepository.getApodWithDate(date);
        return apodWithDate;
        }
}
