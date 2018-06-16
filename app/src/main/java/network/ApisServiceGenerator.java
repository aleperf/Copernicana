package network;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApisServiceGenerator {

    private static final String BASE_NASA_API_URL = "https://api.nasa.gov";

    private static OkHttpClient httpClient =
            new OkHttpClient.Builder().build();


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(BASE_NASA_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()));

    private static Retrofit retrofit = builder.build();

    public static ApisService createApisService() {
        return retrofit.create(ApisService.class);
    }
}
