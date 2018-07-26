package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ApodFavoritesViewModel extends ViewModel {

    private final static int MAX_APOD_LOADED_PER_TIME = 30;
    private AstroRepository astroRepository;
    private MutableLiveData<List<Apod>> favoritesApod;
    private String lastDate;
    private List<Apod> favoriteApodList;


    @Inject
    public ApodFavoritesViewModel(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;
        favoritesApod = new MutableLiveData<>();
        favoriteApodList = new ArrayList<>();
        loadFavoritesApod();
    }


    public MutableLiveData<List<Apod>> getFavoritesApod() {
        return favoritesApod;
    }



    public void loadFavoritesApod() {
        new FavoritesLoaderAsyncTask().execute(lastDate);
    }


    private class FavoritesLoaderAsyncTask extends AsyncTask<String, Void, List<Apod>> {
        private String asyncDate;

        @Override
        protected List<Apod> doInBackground(String... strings) {
            asyncDate = strings[0];
            return astroRepository.getFavoritesApodLessThanDate(asyncDate);
        }

        @Override
        protected void onPostExecute(List<Apod> apodList) {
            if (apodList != null && apodList.size() > 0) {
                String date = apodList.get(apodList.size() - 1).getDate();
                lastDate = date;
                favoriteApodList.addAll(apodList);
                favoritesApod.setValue(favoriteApodList);
            }
        }

    }

    /**
     * Check if the apodList isn't a multiple of the maximum number of apod loaded per time.
     * If it isn't at max size there are certainly no more favorites apod to load (otherwise the size of
     * favoriteApodlist would be a multiple of the max size).
     *@return true if it the size isn't a multiple of the max size, false otherwise.
     */

    public boolean isMultipleOfMaxApodLoadedPerTime(){

        return ((favoriteApodList.size() % MAX_APOD_LOADED_PER_TIME) == 0);
    }


}





