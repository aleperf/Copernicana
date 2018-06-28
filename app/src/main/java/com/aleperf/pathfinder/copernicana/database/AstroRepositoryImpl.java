package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.BuildConfig;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.network.ApisService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AstroRepositoryImpl implements AstroRepository {

    private final String TAG = AstroRepositoryImpl.class.getSimpleName();

    private ApodDao apodDao;
    private ApisService apisService;

    @Inject
    public AstroRepositoryImpl(ApodDao apodDao, ApisService apisService) {
        this.apodDao = apodDao;
        this.apisService = apisService;
    }

    @Override
    public LiveData<List<Apod>> getAllApodOrderDesc() {
        return apodDao.getAllApodOrderDesc();
    }

    @Override
    public LiveData<Apod> getLastApod() {
        return apodDao.getLastApod();
    }

    @Override
    public LiveData<Apod> getApodWithDate(String date) {
        return apodDao.getApodWithDate(date);
    }

    @Override
    public LiveData<Apod> getPreviousApod(String date) {
        return apodDao.getPreviousApod(date);
    }

    @Override
    public LiveData<Apod> getNextApod(String date) {
        return apodDao.getNextApod(date);
    }

    @Override
    public LiveData<Integer> getApodCount() {
        return apodDao.getApodCount();
    }

    @Override
    public void insertApod(Apod apod) {
        if (apod == null) {
            return;
        }
        Completable.fromAction(() -> apodDao.insertApod(apod)).subscribeOn(Schedulers.io());
    }

    @Override
    public void deleteApodWithDate(String date) {
        Completable.fromAction(() -> apodDao.deleteApodWithDate(date)).subscribeOn(Schedulers.io());
    }

    @Override
    public void updateApodIsFavorite(boolean isFavorite, String date) {
        Completable.fromAction(() -> apodDao.updateApodIsFavorite(isFavorite, date)).subscribeOn(Schedulers.io());
    }
    @Override
    public void loadApod(@Nullable String date) {

        Call<Apod> apodCall = apisService.getApod(BuildConfig.MY_API_KEY, date);
        apodCall.enqueue(new Callback<Apod>() {
            @Override
            public void onResponse(Call<Apod> call, Response<Apod> response) {
                Apod apod = response.body();
                if (apod != null) {
                    insertApod(apod);
                }
            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {
                Log.d(TAG, "Failure on in loading data " + t.getMessage());
            }
        });
    }

    public void initializeRepository(){
        //temporary solution
        loadApod(null);
    }
}
