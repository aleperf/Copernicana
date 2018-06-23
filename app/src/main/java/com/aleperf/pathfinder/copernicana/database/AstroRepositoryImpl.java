package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;

import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.List;

public class AstroRepositoryImpl implements AstroRepository {

    @Override
    public LiveData<List<Apod>> getAllApodOrderDesc() {
        return null;
    }

    @Override
    public LiveData<Apod> getLastApod() {
        return null;
    }

    @Override
    public LiveData<Apod> getApodWithDate(String date) {
        return null;
    }

    @Override
    public LiveData<Apod> getPreviousApod(String date) {
        return null;
    }

    @Override
    public LiveData<Apod> getNextApod(String date) {
        return null;
    }

    @Override
    public LiveData<Integer> getApodCount() {
        return null;
    }

    @Override
    public void insertApod(Apod apod) {

    }

    @Override
    public void deleteApodWithDate(String date) {

    }

    @Override
    public void updateApodIsFavorite(boolean isFavorite, Apod apod) {

    }
}
