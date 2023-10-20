package com.victory.game;

import com.victory.game.interfaces.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientWithToken {

    private static final String BASE_URL = "https://victory-ceer.onrender.com";
    private static ApiService apiService;

    public static ApiService getApiService(String token) {
        if (apiService == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer " + token) // Add the token here
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
