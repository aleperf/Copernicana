package com.aleperf.pathfinder.copernicana.intro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.database.UpdateService;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;

import javax.inject.Inject;

public class IntroViewModel extends ViewModel {

    private final static String TAG = IntroViewModel.class.getSimpleName();
    private AstroRepository astroRepository;
    private LiveData<Apod> apod;




    @Inject
    public IntroViewModel(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;

    }


    public LiveData<Apod> getApod() {
        if (apod == null) {
            apod = astroRepository.getLastApod();
            return apod;
        }

        return apod;
    }

    public void initializeRepository(){
        new RepositoryInitializer().execute(astroRepository);
    }



    private static class RepositoryInitializer extends AsyncTask<AstroRepository, Void, Integer> {

        private AstroRepository repository;

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 0) {
                repository.updateRepository();
            }

        }

        @Override
        protected Integer doInBackground(AstroRepository... repositories) {
            this.repository = repositories[0];
            return repository.countApodEntries();
        }
    }

}
