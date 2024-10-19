package com.example.quotationaapp.data.ApiService;

import com.example.quotationaapp.data.model.QuotesData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("quotes/random")
    Call<QuotesData> getQoutes();
}
