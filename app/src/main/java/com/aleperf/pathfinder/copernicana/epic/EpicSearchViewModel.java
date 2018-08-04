package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import java.util.List;

import javax.inject.Inject;

public class EpicSearchViewModel extends ViewModel {

    private MutableLiveData<List<Epic>> naturalEpic;
    private MutableLiveData<List<EpicEnhanced>> enhancedEpic;

    @Inject
    AstroRepository astroRepository;

    public EpicSearchViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
        naturalEpic = new MutableLiveData<>();
        enhancedEpic = new MutableLiveData<>();
    }
}
