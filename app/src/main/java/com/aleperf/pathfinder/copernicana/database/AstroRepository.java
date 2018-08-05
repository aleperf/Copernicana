package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;
import com.aleperf.pathfinder.copernicana.model.IssPassage;
import com.aleperf.pathfinder.copernicana.model.IssPosition;

import java.util.List;

public interface AstroRepository {

    final String ASTRONAUT_FAILED_CONNECTION = "failed connection in AstroRepository 123";
    public final String EPIC_NO_DATA = "No data for epic 1343";
    public final String EPIC_FAILURE_ON_CONNECTION = "failure on connection for epic 34343";

    void updateRepository();

    //APOD (a.k.a Astronomic Picture of the Day) CRUD
    LiveData<List<Apod>> getAllApodOrderDesc();
    LiveData<Apod> getLastApod();
    LiveData<Apod> getApodWithDate(String date);
    Apod getSingleApodWithDate(String date);
    Apod getLatestApod();
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
    void insertEpicEnhancedFromSearch(Epic epic);

    //EpicEnhanced
    LiveData<List<EpicEnhanced>> getAllEnhancedEpic();
    LiveData<List<EpicEnhanced>> getAllEnhancedFavorites();
    LiveData<EpicEnhanced> getEpicEnhancedWithIdentifier(long identifier);
    void insertAllEpicEnhanced(List<EpicEnhanced> epicEnhanced);
    void deleteAllNonFavoritesEpicEnhanced();
    void updateEnhancedFavWithIdentifier(int isFavorite, long identifier);
    void insertEpicEnhancedFromSearch(EpicEnhanced epic);

    //EpicSearch
    void searchEpicNaturalForDate(String date, MutableLiveData<List<Epic>> naturalEpic);
    void searchEpicEnhancedForDate(String date, MutableLiveData<List<EpicEnhanced>> epicEnhanced);

    //Astronaut
    LiveData<List<Astronaut>> getAllAstronauts();
    LiveData<Astronaut> getAstronautWithId(int id);
    void insertAllAstronauts(List<Astronaut> astronauts);
    void deleteAllAstronauts();
    void checkIssPositionNow(MutableLiveData<IssPosition> issPosition);
    void calculateIssPassage(double latitude, double longitude, MutableLiveData<List<IssPassage>> issPassages);


}
