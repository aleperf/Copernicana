package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Epic;

import java.util.List;

import javax.inject.Inject;


public class EpicNaturalFavViewModel extends ViewModel {

    AstroRepository astroRepository;
    LiveData<List<Epic>> favoriteNaturalEpic;

    @Inject
    public EpicNaturalFavViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
        favoriteNaturalEpic = astroRepository.getAllEpicFavorites();
    }

    public LiveData<List<Epic>> getFavoriteNaturalEpic(){
        return favoriteNaturalEpic;
    }

    public void updateNaturalEpic(int isFavorite, long identifier){
        astroRepository.updateEpicFavWithIdentifier(isFavorite, identifier);
    }
}
