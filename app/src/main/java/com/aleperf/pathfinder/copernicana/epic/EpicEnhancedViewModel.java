package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import java.util.List;

import javax.inject.Inject;

public class EpicEnhancedViewModel extends ViewModel {

    private AstroRepository astroRepository;
    private LiveData<List<EpicEnhanced>> enhancedEpic;

    @Inject
    public EpicEnhancedViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
        enhancedEpic = astroRepository.getAllEnhancedEpic();
    }

    public LiveData<List<EpicEnhanced>> getEnhancedEpic(){
        return enhancedEpic;
    }
}
