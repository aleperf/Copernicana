package ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.aleperf.pathfinder.copernicana.BuildConfig;

import model.Apod;
import network.ApisService;
import network.ApisServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private final static String TAG = MainActivityViewModel.class.getSimpleName();

    private MutableLiveData<Apod> apod = new MutableLiveData<>();
    private ApisService apisService;
    private String apiKey = BuildConfig.MY_API_KEY;


    public MainActivityViewModel() {

        apisService = ApisServiceGenerator.createApisService();
        loadApod();
    }

    public MutableLiveData<Apod> getApod() {
        return apod;
    }

    private void loadApod() {
        Call<Apod> apodCall = apisService.getApod(apiKey, null);

        apodCall.enqueue(new Callback<Apod>() {
            @Override
            public void onResponse(Call<Apod> call, Response<Apod> response) {
                apod.setValue(response.body());

            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {
               Log.d(TAG, "Failure on in loading data " + t.getMessage());
            }
        });

    }


}
