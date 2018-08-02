package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;
import com.aleperf.pathfinder.copernicana.model.IssPosition;

import java.util.List;

public interface AstroRepository {

    final String FAILED_CONNECTION = "failed connection in AstroRepository 123";

    void updateRepository();

    //APOD (a.k.a Astronomic Picture of the Day) CRUD
    LiveData<List<Apod>> getAllApodOrderDesc();
    LiveData<Apod> getLastApod();
    LiveData<Apod> getApodWithDate(String date);
    Apod getSingleApodWithDate(String date);
    LiveData<List<Apod>> getAllFavApodOrderDesc();
    Integer countApodEntries();
    void insertApod(Apod apod);
    void insertApodFromSearch(Apod apod);
    void deleteApodWithDate(String date);
    void updateApodIsFavorite(int isFavorite, String date);
    void searchApodForDate(String date, MutableLiveData<Apod> searchedApod);

    //Epic
    LiveData<List<Epic>> getAllNaturalEpic();
    LiveData<List<Epic>> getAllEpicFavorites();
    LiveData<Epic> getEpicWithIdentifier(long identifier);
    void insertAllEpic(List<Epic>epic);
    void deleteAllNonFavoritesEpic();
    void updateEpicFavWithIdentifier(int isFavorite, long identifier);

    //EpicEnhanced
    LiveData<List<EpicEnhanced>> getAllEnhancedEpic();
    LiveData<List<EpicEnhanced>> getAllEnhancedFavorites();
    LiveData<EpicEnhanced> getEpicEnhancedWithIdentifier(long identifier);
    void insertAllEpicEnhanced(List<EpicEnhanced> epicEnhanced);
    void deleteAllNonFavoritesEpicEnhanced();
    void updateEnhancedFavWithIdentifier(int isFavorite, long identifier);

    //Astronaut
    LiveData<List<Astronaut>> getAllAstronauts();
    LiveData<Astronaut> getAstronautWithId(int id);
    void insertAllAstronauts(List<Astronaut> astronauts);
    void deleteAllAstronauts();
    void checkIssPositionNow(MutableLiveData<IssPosition> issPosition);


}
