package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Astronaut;

import java.util.List;

public interface AstroRepository {

    final String FAILED_CONNECTION = "failed connection in AstroRepository 123";

    void initializeRepository();

    //APOD (a.k.a Astronomic Picture of the Day) CRUD
    LiveData<List<Apod>> getAllApodOrderDesc();
    LiveData<Apod> getLastApod();
    LiveData<Apod> getApodWithDate(String date);
    Apod getSingleApodWithDate(String date);
    LiveData<Apod> getPreviousApod(String date);
    LiveData<Apod> getNextApod(String date);
    LiveData<Integer> getApodCount();
    LiveData<List<Apod>> getAllFavApodOrderDesc();
    LiveData<List<Apod>> getAllFavApodOrderDescLessThanDate(String date);
    List<Apod> getFavoritesApodLessThanDate(String date);
    List<Apod> getAllApodLessThanDate(String date);
    void insertApod(Apod apod);
    void insertApodFromSearch(Apod apod);
    void deleteApodWithDate(String date);
    void updateApodIsFavorite(int isFavorite, String date);
    void searchApodForDate(String date, MutableLiveData<Apod> searchedApod);


    //Astronaut
    LiveData<List<Astronaut>> getAllAstronauts();
    LiveData<Astronaut> getAstronautWithId(int id);
    void insertAllAstronauts(List<Astronaut> astronauts);
    void deleteAllAstronauts();


}
