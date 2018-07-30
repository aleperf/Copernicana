package com.aleperf.pathfinder.copernicana.intro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;

import javax.inject.Inject;

public class IntroViewModel extends ViewModel {

    private final static String TAG = IntroViewModel.class.getSimpleName();
    private AstroRepository astroRepository;
    private LiveData<Apod> apod;


    @Inject
    public IntroViewModel(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;
        new RepositoryInitializer().execute(astroRepository);
    }


    public LiveData<Apod> getApod() {
        if (apod == null) {
            apod = astroRepository.getLastApod();
            return apod;
        }

        return apod;
    }

    private static class RepositoryInitializer extends AsyncTask<AstroRepository, Void, Integer> {

        private AstroRepository repository;

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 0) {
                repository.initializeRepository();
            }

        }

        @Override
        protected Integer doInBackground(AstroRepository... repositories) {
            this.repository = repositories[0];
            return repository.countApodEntries();
        }
    }

}
