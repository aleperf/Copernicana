package com.aleperf.pathfinder.copernicana.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.MarsPhoto;

@Database(entities = {Apod.class, Astronaut.class}, version = 1)
public abstract class AstroDatabase extends RoomDatabase {

    public abstract ApodDao apodDao();
    public abstract AstronautDao astronautDao();

}
