package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;

import javax.inject.Inject;


public class EpicNaturalDetailViewModel extends ViewModel {

    AstroRepository astroRepository;

    @Inject
    public EpicNaturalDetailViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
    }


    public void updateEpicNatural(int isFavorite, long identifier){
        astroRepository.updateEpicFavWithIdentifier(isFavorite, identifier);
    }


}
