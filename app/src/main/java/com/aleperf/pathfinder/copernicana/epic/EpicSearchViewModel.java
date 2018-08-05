package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import java.util.ArrayList;
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

    public MutableLiveData<List<Epic>> getNaturalEpic(){
        return naturalEpic;
    }

    public MutableLiveData<List<EpicEnhanced>> getEnhancedEpic(){
        return enhancedEpic;
    }

    public void searchNaturalEpic(String date){
        Log.d("uffa", "sono in ViewModel e sto cercando natural");
        naturalEpic.setValue(new ArrayList<Epic>());
        astroRepository.searchEpicNaturalForDate(date, naturalEpic);
    }

    public void searchEnhancedEpic(String date){
        Log.d("uffa", "sono in ViewModel e sto cercando enhanced");
        enhancedEpic.setValue(new ArrayList<EpicEnhanced>());
        astroRepository.searchEpicEnhancedForDate(date, enhancedEpic);
    }


}
