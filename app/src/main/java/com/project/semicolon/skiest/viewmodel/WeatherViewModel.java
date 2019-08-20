package com.project.semicolon.skiest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.project.semicolon.skiest.model.response.WeatherResponse;
import com.project.semicolon.skiest.repo.WeatherRepo;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepo weatherRepo;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherRepo = new WeatherRepo(application);
    }

    public MutableLiveData<WeatherResponse> getCurrentWeather(String cityName, String lat, String lon) {
        return weatherRepo.getCurrentWeatherResponse(cityName, lat, lon);

    }

}
