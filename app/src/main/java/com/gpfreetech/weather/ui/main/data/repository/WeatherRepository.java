package com.gpfreetech.weather.ui.main.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.gpfreetech.weather.ui.main.data.model.WeatherDataResponse;
import com.gpfreetech.weather.ui.main.data.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    public WeatherRepository(Context context) {

    }

    public MutableLiveData<WeatherDataResponse> getWeathers(String city) {

        final MutableLiveData<WeatherDataResponse> mutableLiveData = new MutableLiveData<>();

        ApiClient.getApiInterface().getWeather(city,ApiClient.API_KEY)
                .enqueue(new Callback<WeatherDataResponse>() {
            @Override
            public void onResponse(Call<WeatherDataResponse> call, Response<WeatherDataResponse> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WeatherDataResponse> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }

}
