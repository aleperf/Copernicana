package com.aleperf.pathfinder.copernicana.intro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.BuildConfig;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.network.ApisService;
import com.aleperf.pathfinder.copernicana.network.ApisServiceGenerator;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private final static String TAG = MainActivityViewModel.class.getSimpleName();
    private AstroRepository astroRepository;
    private static LiveData<Apod> apod;


    @Inject
    public MainActivityViewModel(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;
        apod = astroRepository.getLastApod();
        astroRepository.initializeRepository();
    }


    public LiveData<Apod> getApod() {
        return apod;
    }


}
