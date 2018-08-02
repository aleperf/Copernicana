package com.aleperf.pathfinder.copernicana.epic;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Epic;

import java.util.List;

import javax.inject.Inject;

public class EpicNaturalViewModel extends ViewModel {

    private AstroRepository astroRepository;
    private LiveData<List<Epic>> naturalEpic;

    @Inject
    public EpicNaturalViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
        naturalEpic = astroRepository.getAllNaturalEpic();
    }

    public LiveData<List<Epic>> getNaturalEpic() {
        return naturalEpic;
    }
}
