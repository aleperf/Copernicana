package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;

import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.network.ApisService;

import java.util.List;

import javax.inject.Inject;

public class AstroRepositoryImpl implements AstroRepository {

    ApodDao apodDao;
    ApisService apisService;

    @Inject
    public AstroRepositoryImpl(ApodDao apodDao, ApisService apisService){
        this.apodDao = apodDao;
        this.apisService = apisService;
    }

    @Override
    public LiveData<List<Apod>> getAllApodOrderDesc() {
        return apodDao.getAllApodOrderDesc();
    }

    @Override
    public LiveData<Apod> getLastApod() {
        return apodDao.getLastApod();
    }

    @Override
    public LiveData<Apod> getApodWithDate(String date) {
        return apodDao.getApodWithDate(date);
    }

    @Override
    public LiveData<Apod> getPreviousApod(String date) {
        return apodDao.getPreviousApod(date);
    }

    @Override
    public LiveData<Apod> getNextApod(String date) {
        return apodDao.getNextApod(date);
    }

    @Override
    public LiveData<Integer> getApodCount() {
        return apodDao.getApodCount();
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
