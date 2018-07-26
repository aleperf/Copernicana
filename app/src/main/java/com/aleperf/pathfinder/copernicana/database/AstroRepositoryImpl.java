package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.BuildConfig;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.aleperf.pathfinder.copernicana.network.ApisService;
import com.aleperf.pathfinder.copernicana.utilities.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AstroRepositoryImpl implements AstroRepository {

    private final String TAG = AstroRepositoryImpl.class.getSimpleName();
    private final String FIREBASE_STORAGE = BuildConfig.APP_STORAGE;
    private final String ASTRONAUTS_IN_SPACE = "astronauts/inspace.json";


    private ApodDao apodDao;
    private AstronautDao astronautDao;
    private ApisService apisService;
    private Context context;

    @Inject
    public AstroRepositoryImpl(ApodDao apodDao, AstronautDao astronautDao, ApisService apisService, Context context) {
        this.apodDao = apodDao;
        this.astronautDao = astronautDao;
        this.apisService = apisService;
        this.context = context;
    }

    //Apod section
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
        Completable.fromAction(() -> apodDao.insertApod(apod))
                .subscribeOn(Schedulers.io()).subscribe();

    }

    @Override
    public void insertApodFromSearch(Apod apod) {
        if (apod == null) {
            return;
        }
        Completable.fromAction(() -> apodDao.insertApodFromSearch(apod))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void deleteApodWithDate(String date) {
        Completable.fromAction(() -> apodDao.deleteApodWithDate(date))
                .subscribeOn(Schedulers.io()).subscribe();

    }

    @Override
    public void updateApodIsFavorite(int isFavorite, String date) {
        Completable.fromAction(() -> apodDao.updateApodIsFavorite(isFavorite, date))
                .subscribeOn(Schedulers.io()).subscribe();
    }


    private void loadApod(@Nullable String date) {

        Call<Apod> apodCall = apisService.getApod(BuildConfig.MY_API_KEY, date);
        apodCall.enqueue(new Callback<Apod>() {
            @Override
            public void onResponse(Call<Apod> call, Response<Apod> response) {
                Apod apod = response.body();
                if (apod != null) {
                    insertApod(apod);
                    updateSharedPreferences(apod.getDate());
                }
            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {
                Log.d(TAG, "Failure on in loading data " + t.getMessage());
            }
        });
    }

    private void updateSharedPreferences(String date){
        Resources res = context.getResources();
        SharedPreferences sharedPref = context.getSharedPreferences(res.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(res.getString(R.string.preference_latest_apod_date_key), date);
        editor.apply();
    }

    @Override
    public void searchApodForDate(String date, MutableLiveData<Apod> searchedApod) {
        Call<Apod> apodCall = apisService.getApod(BuildConfig.MY_API_KEY, date);
        apodCall.enqueue(new Callback<Apod>() {
            @Override
            public void onResponse(Call<Apod> call, Response<Apod> response) {
                Apod apod = response.body();
                if (apod != null) {
                    searchedApod.setValue(apod);
                }
            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {
                Log.d(TAG, "Failure on in loading data " + t.getMessage());
                Apod apod = new Apod(null, date,
                        t.getMessage(), null, null, FAILED_CONNECTION,
                        null);
                searchedApod.setValue(apod);
            }
        });

    }

    @Override
    public LiveData<List<Apod>> getAllFavApodOrderDesc() {
        return apodDao.getAllFavApodOrderDesc();
    }

    @Override
    public LiveData<List<Apod>> getAllFavApodOrderDescLessThanDate(String date) {
        return apodDao.getAllFavApodOrderDescLessThanDate(date);
    }






    public void initializeRepository() {
        //temporary solution
        loadApod(null);
        loadAllAstronauts();
    }


    //Astronaut section
    @Override
    public LiveData<List<Astronaut>> getAllAstronauts() {
        return astronautDao.getAllAstronauts();
    }

    @Override
    public LiveData<Astronaut> getAstronautWithId(int id) {
        return astronautDao.getAstronautWithId(id);
    }

    @Override
    public void insertAllAstronauts(List<Astronaut> astronauts) {
        Completable.fromAction(() -> astronautDao.insertAllAstronauts(astronauts))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void deleteAllAstronauts() {
        Completable.fromAction(() -> astronautDao.deleteAllAstronauts())
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void loadAllAstronauts() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(FIREBASE_STORAGE).child(ASTRONAUTS_IN_SPACE);
        try {
            final File jsonFile = File.createTempFile("inspace", "json");
            storageReference.getFile(jsonFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    try {
                        JsonParser parser = new JsonParser();
                        JsonObject jsonObject = (JsonObject) parser.parse(new FileReader(jsonFile));
                        if (jsonObject != null) {
                            //dump old data before inserting new ones.
                            deleteAllAstronauts();
                            List<Astronaut> astronauts = Utils.getAstronautsFromJson(jsonObject);
                            if (astronauts != null) {
                                insertAllAstronauts(astronauts);
                            }
                        }

                    } catch (FileNotFoundException err) {
                        err.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
