package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import java.util.List;

import javax.inject.Inject;

public class EpicEnhancedFavViewModel extends ViewModel {

    AstroRepository astroRepository;
    LiveData<List<EpicEnhanced>> favoriteEnhancedEpic;

    @Inject
    public EpicEnhancedFavViewModel(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;
        favoriteEnhancedEpic = astroRepository.getAllEnhancedFavorites();
    }

    public LiveData<List<EpicEnhanced>> getFavoriteEnhancedEpic() {
        return favoriteEnhancedEpic;
    }
}
