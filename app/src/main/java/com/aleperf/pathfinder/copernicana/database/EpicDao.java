package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aleperf.pathfinder.copernicana.model.Epic;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface EpicDao {

    @Query("SELECT * FROM epic")
    LiveData<List<Epic>> getAllNaturalEpic();

    @Query("SELECT * FROM epic WHERE isFavorite = 1")
    LiveData<List<Epic>> getAllEpicFavorites();

    @Query("SELECT * FROM epic WHERE identifier = :identifier LIMIT 1")
    LiveData<Epic> getEpicWithIdentifier(long identifier);

    @Insert(onConflict = IGNORE)
    void insertAllEpic(List<Epic>epic);

    @Query("DELETE FROM epic WHERE isFavorite = 0")
    void deleteAllNonFavoritesEpic();

    @Query("UPDATE epic SET isFavorite = :isFavorite WHERE identifier = :identifier")
    void updateEpicFavWithIdentifier(int isFavorite, long identifier);

}
