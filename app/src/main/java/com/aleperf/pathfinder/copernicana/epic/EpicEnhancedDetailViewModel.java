package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;


import javax.inject.Inject;

public class EpicEnhancedDetailViewModel extends ViewModel {

    AstroRepository astroRepository;

    @Inject
    public EpicEnhancedDetailViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
    }


    public void updateEpicEnhanced(int isFavorite, long identifier){
        astroRepository.updateEnhancedFavWithIdentifier(isFavorite, identifier);
    }
}
