package com.gpfreetech.weather.ui.main.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpfreetech.weather.ui.main.data.model.WeatherDataResponse;
import com.gpfreetech.weather.ui.main.data.model.WeatherDetail;
import com.gpfreetech.weather.ui.main.data.repository.WeatherRepository;
import com.gpfreetech.weather.ui.main.data.local.WeatherDatabase;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    private WeatherDatabase db;
    private MutableLiveData<WeatherDataResponse> weatherDataResponseLiveData =new MutableLiveData<>();

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherRepository = new WeatherRepository(application.getApplicationContext());
        db=WeatherDatabase.Companion.invoke(application);
    }

    public MutableLiveData<WeatherDataResponse> getWeatherByCity(String city) {
        return weatherRepository.getWeathers(city);
    }

    public LiveData<List<WeatherDetail>> getSearchedCity() {
        return db.getWeatherDao().fetchSearchCity();
    }

    public void addSearchCity(WeatherDetail weatherDetail) {
        db.getWeatherDao().addWeather(weatherDetail);
    }

    public MutableLiveData<WeatherDataResponse> searchByCity(String city) {
        return getWeatherByCity(city);
    }
}
