package com.aleperf.pathfinder.copernicana.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.BuildConfig;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;
import com.aleperf.pathfinder.copernicana.model.IssPosition;
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
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AstroRepositoryImpl implements AstroRepository {

    private final String TAG = AstroRepositoryImpl.class.getSimpleName();
    private final String FIREBASE_STORAGE = BuildConfig.APP_STORAGE;
    private final String ASTRONAUTS_IN_SPACE = "astronauts/inspace.json";



    private ApodDao apodDao;
    private EpicDao epicDao;
    private EpicEnhancedDao epicEnhancedDao;
    private AstronautDao astronautDao;
    private ApisService apisService;
    private Context context;

    @Inject
    public AstroRepositoryImpl(ApodDao apodDao, EpicDao epicDao, EpicEnhancedDao epicEnhancedDao,
                               AstronautDao astronautDao, ApisService apisService, Context context) {
        this.apodDao = apodDao;
        this.epicDao = epicDao;
        this.epicEnhancedDao = epicEnhancedDao;
        this.astronautDao = astronautDao;
        this.apisService = apisService;
        this.context = context;
    }

    //------------------------------- APOD SECTION ---------------------------------
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
    public Apod getLatestApod() {
        return apodDao.getLatestApod();
    }

    @Override
    public Integer countApodEntries() {
        return apodDao.countApodEntries();
    }

    @Override
    public Apod getSingleApodWithDate(String date) {
        return apodDao.getSingleApodWithDate(date);
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
                    updateSharedPreferences(apod);
                }
            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {
                Log.d(TAG, "Failure on in loading data " + t.getMessage());

            }
        });
    }


    private void updateSharedPreferences(Apod apod) {
        Resources res = context.getResources();
        SharedPreferences sharedPref = context.getSharedPreferences(res.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(res.getString(R.string.preference_latest_apod_date_key), apod.getDate());
        editor.putString(res.getString(R.string.preference_latest_apod_title_key), apod.getTitle());
        editor.putString(res.getString(R.string.preference_lastest_apod_media_type_key), apod.getMediaType());
        editor.putString(res.getString(R.string.preference_latest_apod_image_key), apod.getUrl());
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


    public void updateRepository() {
        //temporary solution
        loadApod(null);
        loadAllAstronauts();
        loadEpicNatural();
        loadEpicEnhanced();
    }


    //-------------------------EPIC NATURAL SECTION -----------------------------------


    @Override
    public LiveData<List<Epic>> getAllNaturalEpic() {
        return epicDao.getAllNaturalEpic();
    }

    @Override
    public LiveData<List<Epic>> getAllEpicFavorites() {
        return epicDao.getAllEpicFavorites();
    }

    @Override
    public LiveData<Epic> getEpicWithIdentifier(long identifier) {
        return epicDao.getEpicWithIdentifier(identifier);
    }

    @Override
    public void insertAllEpic(List<Epic> epic) {
        if (epic == null) {
            return;
        }
        Completable.fromAction(() -> epicDao.insertAllEpic(epic))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void deleteAllNonFavoritesEpic() {
        Completable.fromAction(() -> epicDao.deleteAllNonFavoritesEpic())
                .subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void updateEpicFavWithIdentifier(int isFavorite, long identifier) {
        Completable.fromAction(() -> epicDao.updateEpicFavWithIdentifier(isFavorite, identifier))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    public void loadEpicNatural() {
        Call<List<Epic>> epicCall = apisService.getEpicNatural(BuildConfig.MY_API_KEY);
        epicCall.enqueue(new Callback<List<Epic>>() {
            @Override
            public void onResponse(Call<List<Epic>> call, Response<List<Epic>> response) {
                List<Epic> naturalEpic = response.body();
                if (naturalEpic != null) {
                    Completable.fromAction(() -> epicDao.deleteAllNonFavoritesEpic())
                            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            insertAllEpic(naturalEpic);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Epic>> call, Throwable t) {
                Log.d(TAG, "Failure on in loading data " + t.getMessage());
            }
        });
    }

    @Override
    public void searchEpicNaturalForDate(String date, MutableLiveData<List<Epic>> naturalEpic) {
        Call<List<Epic>> epicCall = apisService.getEpicNaturalForDate(date, BuildConfig.MY_API_KEY);
        epicCall.enqueue(new Callback<List<Epic>>() {
            @Override
            public void onResponse(Call<List<Epic>> call, Response<List<Epic>> response) {
                if(response.body() != null ){
                    if(response.body().size() > 0){
                        Log.d("uffa", "sono in astrorepository e response è diverso da null");
                        naturalEpic.setValue(response.body());
                    } else {
                        Epic epic = new Epic(0, NO_DATA, null,
                                null, null,null,
                                null, null, 0);
                        List<Epic> noDataList = new ArrayList<>();
                        noDataList.add(epic);
                        naturalEpic.setValue(noDataList);
                        Log.d("uffa", "sono in astrorepository e response è null");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Epic>> call, Throwable t) {
                Epic epic = new Epic(0, FAILURE_ON_CONNECTION, null,
                        null, null,null,
                        null, null, 0);
                List<Epic> noDataList = new ArrayList<>();
                noDataList.add(epic);
                naturalEpic.setValue(noDataList);
                Log.d("uffa", "sono in astrorepository e la connessione è fallita");

            }
        });

    }

    //------------------- EPIC ENHANCED SECTION -------------------------------------------

    @Override
    public LiveData<List<EpicEnhanced>> getAllEnhancedEpic() {
        return epicEnhancedDao.getAllEnhancedEpic();
    }

    @Override
    public LiveData<List<EpicEnhanced>> getAllEnhancedFavorites() {
        return epicEnhancedDao.getAllEnhancedFavorites();
    }

    @Override
    public LiveData<EpicEnhanced> getEpicEnhancedWithIdentifier(long identifier) {
        return epicEnhancedDao.getEpicEnhancedWithIdentifier(identifier);
    }

    @Override
    public void insertAllEpicEnhanced(List<EpicEnhanced> epicEnhanced) {
        if (epicEnhanced == null) {
            return;
        }
        Completable.fromAction(() -> epicEnhancedDao.insertAllEpicEnhanced(epicEnhanced))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void deleteAllNonFavoritesEpicEnhanced() {
        Completable.fromAction(() -> epicEnhancedDao.deleteAllNonFavoritesEpicEnhanced())
                .subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void updateEnhancedFavWithIdentifier(int isFavorite, long identifier) {
        Completable.fromAction(() -> epicEnhancedDao.updateEnhancedFavWithIdentifier(isFavorite, identifier))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    public void loadEpicEnhanced() {
        Call<List<EpicEnhanced>> epicEnhancedCall = apisService.getEpicEnhanced(BuildConfig.MY_API_KEY);
        epicEnhancedCall.enqueue(new Callback<List<EpicEnhanced>>() {
            @Override
            public void onResponse(Call<List<EpicEnhanced>> call, Response<List<EpicEnhanced>> response) {
                List<EpicEnhanced> epics = response.body();
                if (epics != null) {
                    Completable.fromAction(() -> epicEnhancedDao.deleteAllNonFavoritesEpicEnhanced())
                            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            insertAllEpicEnhanced(epics);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<List<EpicEnhanced>> call, Throwable t) {
                Log.d(TAG, "Failure on in loading data " + t.getMessage());
            }
        });
    }

    @Override
    public void searchEpicEnhancedForDate(String date, MutableLiveData<List<EpicEnhanced>> epicEnhanced) {
        Call<List<EpicEnhanced>> epicEnhancedCall = apisService.getEpicEnhancedForDate(date, BuildConfig.MY_API_KEY);
        epicEnhancedCall.enqueue(new Callback<List<EpicEnhanced>>() {
            @Override
            public void onResponse(Call<List<EpicEnhanced>> call, Response<List<EpicEnhanced>> response) {
                if(response.body() != null ){
                    if(response.body().size() > 0){
                       epicEnhanced.setValue(response.body());
                    } else {
                        EpicEnhanced epic = new EpicEnhanced(0, NO_DATA, null,
                                null, null,null,
                                null, null, 0);
                        List<EpicEnhanced> noDataList = new ArrayList<>();
                        epicEnhanced.setValue(noDataList);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<EpicEnhanced>> call, Throwable t) {
                EpicEnhanced epic = new EpicEnhanced(0, FAILURE_ON_CONNECTION, null,
                        null, null,null,
                        null, null, 0);
                List<EpicEnhanced> noDataList = new ArrayList<>();
                epicEnhanced.setValue(noDataList);
            }
        });

    }

    //--------------------- ASTRONAUTS SECTION -----------------------------------------------
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

    @Override
    public void checkIssPositionNow(MutableLiveData<IssPosition> issPosition) {
        Call<IssPosition> issPositionCall = apisService.getLatestIssPosition();
        issPositionCall.enqueue(new Callback<IssPosition>() {
            @Override
            public void onResponse(Call<IssPosition> call, Response<IssPosition> response) {
                IssPosition position = response.body();

                issPosition.setValue(response.body());
            }

            @Override
            public void onFailure(Call<IssPosition> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                IssPosition noPosition = new IssPosition(0, null, IssPosition.NO_CONNECTION);
                issPosition.setValue(noPosition);
            }
        });
    }

}
