package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ApodDisplayAllViewModel extends ViewModel {

    private final static int MAX_APOD_LOADED_PER_TIME = 30;
    private ApodHelper apodHelper;


    @Inject
    public ApodDisplayAllViewModel(AstroRepository astroRepository) {
        apodHelper = new ApodHelper(new MutableLiveData<List<Apod>>(), new ArrayList<Apod>(), astroRepository);
        loadAllApods();
    }


    public MutableLiveData<List<Apod>> getAllApods(){
        return apodHelper.getApodLoaded();
    }

    public void loadAllApods(){
        new AllApodsLoaderAsyncTask().execute(apodHelper);
    }

    public boolean isMultipleOfMaxApodLoadedPerTime() {

        return ((apodHelper.getApodListSize() % MAX_APOD_LOADED_PER_TIME) == 0);
    }

    private static class AllApodsLoaderAsyncTask extends AsyncTask<ApodHelper, Void, List<Apod>>{

        ApodHelper helper;

        @Override
        protected List<Apod> doInBackground(ApodHelper... apodHelpers) {
            helper = apodHelpers[0];
            return helper.getAstroRepository().getAllApodLessThanDate(helper.getDate());
        }

        @Override
        protected void onPostExecute(List<Apod> apodList) {
            if(apodList != null && apodList.size() > 0){
                helper.updateApodHelper(apodList);
            }
        }
    }


}
