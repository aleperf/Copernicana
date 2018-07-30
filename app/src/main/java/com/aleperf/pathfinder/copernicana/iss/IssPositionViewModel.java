package com.aleperf.pathfinder.copernicana.iss;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.IssPosition;

import javax.inject.Inject;

public class IssPositionViewModel extends ViewModel {

    AstroRepository astroRepository;
    MutableLiveData<IssPosition> issPositionMLD;

    @Inject
    public IssPositionViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
        issPositionMLD = new MutableLiveData<>();
    }

    public MutableLiveData<IssPosition> getIssPositionMLD() {
        return issPositionMLD;
    }

    public void checkIssPositionNow(){
        astroRepository.checkIssPositionNow(issPositionMLD);
        }
}
