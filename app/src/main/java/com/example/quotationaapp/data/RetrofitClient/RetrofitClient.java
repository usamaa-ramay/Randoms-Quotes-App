package com.example.quotationaapp.data.RetrofitClient;

import com.example.quotationaapp.data.ApiService.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.OffsetDateTime;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public final static String BASE_URL = "https://dummyjson.com/";
    private static ApiService getApiService = null;

    public static ApiService getApiService() {
        if (getApiService == null) {
            getApiService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
        }
        return getApiService;
    }
}
