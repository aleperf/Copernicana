package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;

import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.List;

public interface AstroRepository {

    //APOD (a.k.a Astronomic Picture of the Day) CRUD
    LiveData<List<Apod>> getAllApodOrderDesc();
    LiveData<Apod> getLastApod();
    LiveData<Apod> getApodWithDate(String date);
    LiveData<Apod> getPreviousApod(String date);
    LiveData<Apod> getNextApod(String date);
    LiveData<Integer> getApodCount();
    void insertApod(Apod apod);
    void deleteApodWithDate(String date);
    void updateApodIsFavorite(boolean isFavorite, String date);
}
