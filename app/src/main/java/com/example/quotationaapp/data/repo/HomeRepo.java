package com.example.quotationaapp.data.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quotationaapp.data.RetrofitClient.RetrofitClient;
import com.example.quotationaapp.data.model.QuotesData;
import com.example.quotationaapp.data.utils.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepo {
    private static MutableLiveData<BaseResponse<QuotesData>> qouteData ;

    public LiveData<BaseResponse<QuotesData>> getQuotesData(){
        if (qouteData == null){
            qouteData = new MutableLiveData<>();
        }
        return qouteData;
    }

    public void getQoute() {
        qouteData.postValue(BaseResponse.loading());
        RetrofitClient.getApiService().getQoutes().enqueue(new Callback<QuotesData>() {
            @Override
            public void onResponse(Call<QuotesData> call, Response<QuotesData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    qouteData.postValue(BaseResponse.success(response.body()));
                }
            }

            @Override
            public void onFailure(Call<QuotesData> call, Throwable t) {
                qouteData.postValue(BaseResponse.error("Data not found"));
                Log.d("fsfs",t.getLocalizedMessage());
            }
        });

    }
}
