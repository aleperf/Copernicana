package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ApodFavoritesViewModel extends ViewModel {

    private AstroRepository astroRepository;
    private MutableLiveData<List<Apod>> favoritesApod;
    private MutableLiveData<List<Apod>> last30Apod;
    private String lastDate;
    private List<Apod> favoriteApodList;

    @Inject
    public ApodFavoritesViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
        favoritesApod = new MutableLiveData<>();
        favoriteApodList = new ArrayList<>();
    }


    public MutableLiveData<List<Apod>> getFavoritesApod(){
        return favoritesApod;
    }

    public LiveData<List<Apod>> loadFavoritesApod(){
        return astroRepository.getAllFavApodOrderDesc();
    }

    public LiveData<List<Apod>> loadFavoritesApodLessThenDate(String date){
          this.lastDate = date;
          return astroRepository.getAllFavApodOrderDescLessThanDate(date);
    }

    public String getlastDate(){
        return lastDate;
    }

    public void setLastDate(String date){
        lastDate = date;
    }

    public void setFavoritesApod(List<Apod> apodList, String date){

        if(favoriteApodList.size() > 0 && !lastDate.equals(date)){
            lastDate = date;
            favoriteApodList.addAll(apodList);
            favoritesApod.setValue(favoriteApodList);
        }


    }


    }





