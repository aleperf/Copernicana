package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aleperf.pathfinder.copernicana.model.Astronaut;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface AstronautDao {

    @Query("SELECT * FROM astronaut")
    LiveData<Astronaut> getAllAstronauts();

    @Query("SELECT * FROM astronaut WHERE :id = id")
    LiveData<Astronaut> getAstronautWithId(int id);

    @Insert(onConflict = REPLACE)
    long insertAllAstronauts(List<Astronaut> astronauts);

    @Query("DELETE FROM astronaut")
    void deleteAllAstronauts();

}
