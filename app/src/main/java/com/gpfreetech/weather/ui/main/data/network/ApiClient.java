package com.gpfreetech.weather.ui.main.data.network;


import com.gpfreetech.weather.ui.main.data.model.WeatherDataResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiClient {

    public static final String API_KEY = "094aa776d64c50d5b9e9043edd4ffd00";

    public static String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private static ApiInterface apiInterface;

    public static ApiInterface getApiInterface() {
        if (apiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInterface = retrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }

    public interface ApiInterface {

        @GET("weather")
        Call<WeatherDataResponse> getWeather(@Query("q") String city_name, @Query("appid") String appid);

    }

}
