package com.aleperf.pathfinder.copernicana.injection;

import com.aleperf.pathfinder.copernicana.network.ApisService;
import com.google.gson.GsonBuilder;

import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApisServiceModule {

    private static final String BASE_URL = "https://api.nasa.gov";

    @Provides
    @CopernicanaApplicationScope
    public ApisService provideApisService(Retrofit retrofit) {
        return retrofit.create(ApisService.class);
    }

    @Provides
    @CopernicanaApplicationScope
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @CopernicanaApplicationScope
    public GsonConverterFactory provideGsonConverterFactory(){
        return GsonConverterFactory.create(new GsonBuilder().create());
    }

    @Provides
    @CopernicanaApplicationScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory).build();
    }

}
