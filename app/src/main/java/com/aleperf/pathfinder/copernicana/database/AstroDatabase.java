package com.aleperf.pathfinder.copernicana.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;


@Database(entities = {Apod.class, Astronaut.class, Epic.class, EpicEnhanced.class}, version = 1)
@TypeConverters(EpicTypeConverter.class)
public abstract class AstroDatabase extends RoomDatabase {

    public abstract ApodDao apodDao();
    public abstract AstronautDao astronautDao();
    public abstract EpicDao epicDao();
    public abstract EpicEnhancedDao epicEnhancedDao();

}
