package com.unitapplications.postmakerwithunsplashapi.Api_files;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {
    public static final String BASE_URL = "https://api.unsplash.com";
    public static final String ACCESS_KEY = "Gof500q-sL_96RfcNO_bW8NiU6GJjK3Ta6xkZ-erbDI";

    public static Retrofit retrofit = null;

    public static ApiInterface getApiInterface(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        if (retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}