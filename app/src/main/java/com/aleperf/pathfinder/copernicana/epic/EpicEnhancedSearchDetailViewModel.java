package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import javax.inject.Inject;


public class EpicEnhancedSearchDetailViewModel extends ViewModel{


    AstroRepository astroRepository;
    @Inject
    public EpicEnhancedSearchDetailViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
    }

    public void insertEpicEnhancedFromSearch(EpicEnhanced epic){
        astroRepository.insertEpicEnhancedFromSearch(epic);
    }
}
