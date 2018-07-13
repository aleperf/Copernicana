package com.aleperf.pathfinder.copernicana.network;

import android.support.annotation.NonNull;

import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.model.Epic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApisService {

    /**
     * Query the NASA APOD API for the image of the day
     * @param apiKey necessary String parameter to query the APOD API, can't be null
     * @param date a String representing a date in the format YYYY-MM-DD, it is an optional query parameter
     * @return the Nasa image of the day if date is null, otherwise it returns the image of the day
     * for the date requested.
     */
    @GET("planetary/apod")
    Call<Apod> getApod(@NonNull @Query("api_key") String apiKey, @Query("date") String date);

    @GET("EPIC/api/natural/images")
    Call<List<Epic>> getEpicNatural(@Query("api_key") String apiKey);

    @GET("EPIC/api/natural/date/{date}")
    Call<List<Epic>> getEpicNaturalForDate(@Path("date") String date, @Query("api_key") String apiKey);

    @GET("EPIC/api/enhanced/images")
    Call<List<Epic>> getEpicEnhanced(@Query("api_key") String apiKey);

    @GET("EPIC/api/enhanced/date/{date}")
    Call<List<Epic>> getEpicEnhancedForDate(@Path("date") String date, @Query("api_key") String apiKey);



}
