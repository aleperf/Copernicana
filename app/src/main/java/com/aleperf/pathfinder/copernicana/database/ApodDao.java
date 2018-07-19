package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ApodDao {

    @Query("SELECT * FROM apod ORDER BY date DESC LIMIT 1" )
    LiveData<Apod> getLastApod();

    @Query("SELECT * FROM apod WHERE date = :date LIMIT 1")
    LiveData<Apod> getApodWithDate(String date);

    @Query("SELECT * FROM apod ORDER BY DATE DESC")
    LiveData<List<Apod>> getAllApodOrderDesc();

    @Query("SELECT * FROM apod WHERE date < :date ORDER BY date DESC LIMIT 1")
    LiveData<Apod> getPreviousApod(String date);

    @Query("SELECT * FROM apod WHERE date > :date ORDER BY date LIMIT 1")
    LiveData<Apod> getNextApod(String date);

    @Query("SELECT COUNT(*) FROM apod")
    LiveData<Integer> getApodCount();

    @Insert(onConflict = IGNORE)
    long insertApod(Apod apod);

    @Query("DELETE FROM apod WHERE date = :date")
    void deleteApodWithDate(String date);

    @Query("UPDATE apod SET  isFavorite = :isFavorite WHERE date = :date")
    void updateApodIsFavorite(boolean isFavorite, String date);


}
