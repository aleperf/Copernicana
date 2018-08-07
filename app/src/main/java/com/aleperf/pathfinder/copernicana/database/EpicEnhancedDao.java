package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface EpicEnhancedDao {
    @Query("SELECT * FROM epic_enhanced ORDER BY date DESC")
    LiveData<List<EpicEnhanced>> getAllEnhancedEpic();

    @Query("SELECT * FROM epic_enhanced WHERE isFavorite = 1 ORDER BY DATE DESC")
    LiveData<List<EpicEnhanced>> getAllEnhancedFavorites();

    @Query("SELECT * FROM epic_enhanced WHERE identifier = :identifier LIMIT 1")
    LiveData<EpicEnhanced> getEpicEnhancedWithIdentifier(long identifier);

    @Insert(onConflict = IGNORE)
    void insertAllEpicEnhanced(List<EpicEnhanced> epicEnhanced);

    @Insert(onConflict = REPLACE)
    void insertEpicFromSearch(EpicEnhanced epic);

    @Query("DELETE FROM epic_enhanced WHERE isFavorite = 0")
    void deleteAllNonFavoritesEpicEnhanced();

    @Query("UPDATE epic_enhanced SET isFavorite = :isFavorite WHERE identifier = :identifier")
    void updateEnhancedFavWithIdentifier(int isFavorite, long identifier);
}
