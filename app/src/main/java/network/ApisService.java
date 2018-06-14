package network;

import model.Apod;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApisService {

    @GET
    Call<Apod> getApod();

    @GET()
    Call<Apod> getApodForDate(String date);

}
