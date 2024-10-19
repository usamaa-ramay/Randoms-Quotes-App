package com.example.quotationaapp.ui.home.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quotationaapp.data.RetrofitClient.RetrofitClient;
import com.example.quotationaapp.data.model.QuotesData;
import com.example.quotationaapp.data.repo.HomeRepo;
import com.example.quotationaapp.data.utils.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeModelView extends ViewModel {
    static HomeRepo homeRepo = new HomeRepo();
    public static LiveData<BaseResponse<QuotesData>> _qoute = homeRepo.getQuotesData();


    public void setQoute(){
        homeRepo.getQoute();

    }
}
