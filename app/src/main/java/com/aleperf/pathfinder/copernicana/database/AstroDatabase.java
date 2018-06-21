package com.aleperf.pathfinder.copernicana.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.aleperf.pathfinder.copernicana.model.Apod;

@Database(entities = {Apod.class}, version = 1)
public abstract class AstroDatabase extends RoomDatabase {
}
