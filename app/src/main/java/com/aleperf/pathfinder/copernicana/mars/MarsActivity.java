package com.aleperf.pathfinder.copernicana.mars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.BuildConfig;
import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.MarsPhoto;
import com.aleperf.pathfinder.copernicana.network.ApisService;
import com.aleperf.pathfinder.copernicana.network.MarsQuery;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarsActivity extends AppCompatActivity {

    @Inject
    ApisService apisService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars);


        loadMarsPhotos();
    }

    private void loadMarsPhotos(){
     Call<MarsQuery> marsQueryCall = apisService.getOpportunityLatestPhotos(1, BuildConfig.MY_API_KEY);

     marsQueryCall.enqueue(new Callback<MarsQuery>() {
         @Override
         public void onResponse(Call<MarsQuery> call, Response<MarsQuery> response) {
             List<MarsPhoto> marsPhotos = response.body().getPhotos();
             if(marsPhotos != null){
                 int size = marsPhotos.size();
                 MarsPhoto firstPhoto = marsPhotos.get(0);
                 String photoUrl = firstPhoto.getImageUrl();
                 String date = firstPhoto.getEarthDate();
                 int id = firstPhoto.getId();
                 int sol = firstPhoto.getSol();
                 String roverName = firstPhoto.getRover().getName();
                 String roverCameraName = firstPhoto.getRoverCamera().getName();
                 String roverCameraFullName = firstPhoto.getRoverCamera().getFullName();
                 Log.d("uffa", "data size: "+ size + " photoUrl: " + photoUrl + " date: " + date +
                 " id " + id + " sol: " + sol + " rover name = " + roverName +
                 " camera name = " + roverCameraName + " rover camera full name = " + roverCameraFullName);

             } else {
                 Log.d("uffa", "mars photos is null");
             }
         }

         @Override
         public void onFailure(Call<MarsQuery> call, Throwable t) {
                 Log.d("uffa", "failure in loading " + t.getMessage());
         }
     });
    }
}
