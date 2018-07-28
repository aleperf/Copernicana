package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.network.ApisService;

import javax.inject.Inject;

public class ApodSearchViewModel extends ViewModel {

    private AstroRepository astroRepository;
    private MutableLiveData<Apod> apodSearched;
    private boolean isInDatabase;


    @Inject
    public ApodSearchViewModel(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;
        apodSearched = new MutableLiveData<>();
    }

    public MutableLiveData<Apod> getApodSearched() {
        return apodSearched;
    }

    public void searchApodForDate(String date) {
       new DatabaseSearcherAsyncTask().execute(date);
    }

    public void updateApodInDatabase(int isFavorite, String date){
        astroRepository.updateApodIsFavorite(isFavorite, date);
    }

    /**
     * Add an apod to the database, if there is a conflict (the Apod is alredady in the
     * database) the row is replaced.
     * @param apod, the Apod to insert.
     */

    public void addApodToTheDatabase(Apod apod) {
        astroRepository.insertApodFromSearch(apod);
    }

    public void removeApodFromDatabase(String date) {
        astroRepository.deleteApodWithDate(date);
    }

    private class DatabaseSearcherAsyncTask extends AsyncTask<String, Void, Apod>{

        private String date;

        @Override
        protected Apod doInBackground(String... strings) {
            date = strings[0];
            return astroRepository.getSingleApodWithDate(date);
        }
        @Override
        protected void onPostExecute(Apod apod) {
           if(apod != null){
               isInDatabase = true;
               apodSearched.setValue(apod);
           } else {
               isInDatabase = false;
               astroRepository.searchApodForDate(date, apodSearched);
           }
        }
    }

    public boolean isInDatabase() {
        return isInDatabase;
    }
}
