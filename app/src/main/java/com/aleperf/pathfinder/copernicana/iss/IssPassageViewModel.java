package com.aleperf.pathfinder.copernicana.iss;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.IssPassage;

import java.util.List;

import javax.inject.Inject;


public class IssPassageViewModel extends ViewModel {

    MutableLiveData<List<IssPassage>> issPassages;

    AstroRepository astroRepository;

    @Inject
    public IssPassageViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
        issPassages = new MutableLiveData<>();
    }

    public MutableLiveData<List<IssPassage>> getIssPassages(){
        return issPassages;
    }

    public void calculateIssPassages(double latitude, double longitude){
        astroRepository.calculateIssPassage(latitude, longitude, issPassages);
    }
}
