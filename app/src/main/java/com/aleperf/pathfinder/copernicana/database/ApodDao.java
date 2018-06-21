package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.List;

@Dao
public interface ApodDao {

    @Query("SELECT * FROM apod ORDER BY date DESC LIMIT 1" )
    LiveData<Apod> getLastApod();

    @Query("SELECT * FROM apod WHERE :date = date LIMIT 1")
    LiveData<Apod> getApodForDate(String date);

    @Query("SELECT * FROM apod ORDER BY DATE DESC")
    LiveData<List<Apod>> getAllApodStartFromLast();

    @Query("SELECT * FROM apod WHERE :date < date ORDER BY date DESC LIMIT 1")
    LiveData<Apod> getPreviousApod(String date);

    @Query("SELECT * FROM apod WHERE :date > date ORDER BY date DESC LIMIT 1")
    LiveData<Apod> getNextApod(String date);

    @Query("SELECT COUNT(*) FROM apod")
    LiveData<Integer> getApodCount();




}
