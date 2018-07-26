package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.MutableLiveData;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.List;

/**
 * ApodHelper is an helper class for passing data about Apod to an AsyncTask.
 */

public class ApodHelper {

    private MutableLiveData<List<Apod>> apodLoaded;
    private AstroRepository astroRepository;
    private List<Apod> apodList;

    public ApodHelper(MutableLiveData<List<Apod>> apodLoaded, List<Apod> apodList, AstroRepository astroRepository) {
        this.apodLoaded = apodLoaded;
        this.apodList = apodList;
        this.astroRepository = astroRepository;
    }



    public String getDate() {
       if(apodList != null && apodList.size() > 0){
           return apodList.get(apodList.size() - 1).getDate();
       } else {
           return null;
       }
    }


    public void updateApodHelper(List<Apod> apods){
        apodList.addAll(apods);
        apodLoaded.setValue(apodList);
    }

    public int getApodListSize(){
        return apodList.size();
    }

    public MutableLiveData<List<Apod>> getApodLoaded() {
        return apodLoaded;
    }

    public AstroRepository getAstroRepository() {
        return astroRepository;
    }
}
