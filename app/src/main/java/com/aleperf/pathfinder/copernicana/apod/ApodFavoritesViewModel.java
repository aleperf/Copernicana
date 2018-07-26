package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ApodFavoritesViewModel extends ViewModel {

    private final static int MAX_APOD_LOADED_PER_TIME = 30;
    private ApodHelper apodHelper;


    @Inject
    public ApodFavoritesViewModel(AstroRepository astroRepository) {
        apodHelper = new ApodHelper(new MutableLiveData<List<Apod>>(), new ArrayList<Apod>(), astroRepository);
        loadFavoritesApod();
    }


    public MutableLiveData<List<Apod>> getFavoritesApod() {
        return apodHelper.getApodLoaded();
    }


    public void loadFavoritesApod() {
        new FavoritesLoaderAsyncTask().execute(apodHelper);
    }


    private static class FavoritesLoaderAsyncTask extends AsyncTask<ApodHelper, Void, List<Apod>> {

        ApodHelper helper;

        @Override
        protected List<Apod> doInBackground(ApodHelper... apodHelpers) {
            this.helper = apodHelpers[0];
            return helper.getAstroRepository().getFavoritesApodLessThanDate(helper.getDate());

        }

        @Override
        protected void onPostExecute(List<Apod> apodList) {
            if (apodList != null && apodList.size() > 0) {
                helper.updateApodHelper(apodList);
            }
        }

    }

    /**
     * Check if the apodList isn't a multiple of the maximum number of apod loaded per time.
     * If it isn't at max size there are certainly no more favorites apod to load (otherwise the size of
     * favoriteApodlist would be a multiple of the max size).
     *
     * @return true if it the size isn't a multiple of the max size, false otherwise.
     */

    public boolean isMultipleOfMaxApodLoadedPerTime() {

        return ((apodHelper.getApodListSize() % MAX_APOD_LOADED_PER_TIME) == 0);
    }


}





