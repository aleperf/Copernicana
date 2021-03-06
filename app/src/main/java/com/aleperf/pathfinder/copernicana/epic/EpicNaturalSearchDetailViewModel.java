package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Epic;

import javax.inject.Inject;

public class EpicNaturalSearchDetailViewModel extends ViewModel {

    AstroRepository astroRepository;
    @Inject
    public EpicNaturalSearchDetailViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
    }

    public void insertEpicNaturalFromSearch(Epic epic){
        astroRepository.insertEpicEnhancedFromSearch(epic);
    }
}
