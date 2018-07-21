package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Astronaut;

import java.util.List;

public interface AstroRepository {

    void initializeRepository();

    //APOD (a.k.a Astronomic Picture of the Day) CRUD
    LiveData<List<Apod>> getAllApodOrderDesc();
    LiveData<Apod> getLastApod();
    LiveData<Apod> getApodWithDate(String date);
    LiveData<Apod> getPreviousApod(String date);
    LiveData<Apod> getNextApod(String date);
    LiveData<Integer> getApodCount();
    void insertApod(Apod apod);
    void deleteApodWithDate(String date);
    void updateApodIsFavorite(int isFavorite, String date);


    //Astronaut
    LiveData<List<Astronaut>> getAllAstronauts();
    LiveData<Astronaut> getAstronautWithId(int id);
    void insertAllAstronauts(List<Astronaut> astronauts);
    void deleteAllAstronauts();


}
