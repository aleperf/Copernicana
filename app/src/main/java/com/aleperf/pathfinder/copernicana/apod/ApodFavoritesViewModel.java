package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ApodFavoritesViewModel extends ViewModel {

    private final static int MAX_APOD_LOADED_PER_TIME = 30;
    //private ApodHelper apodHelper;
    private AstroRepository astroRepository;
    private LiveData<List<Apod>> apodFavorites;


    @Inject
    public ApodFavoritesViewModel(AstroRepository astroRepository) {
       this.astroRepository = astroRepository;
        loadFavoritesApod();
    }


    public LiveData<List<Apod>> getFavoritesApod() {
        return apodFavorites;
    }


    public void loadFavoritesApod() {
        apodFavorites = astroRepository.getAllFavApodOrderDesc();
    }


}





