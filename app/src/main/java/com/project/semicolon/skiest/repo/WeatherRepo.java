package com.project.semicolon.skiest.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.project.semicolon.skiest.model.response.WeatherResponse;
import com.project.semicolon.skiest.service.RestApi;
import com.project.semicolon.skiest.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepo {
    private Application application;
    private RestApi restApi;
    private static final String TAG = WeatherRepo.class.getSimpleName();

    public WeatherRepo(Application application) {
        this.application = application;
        restApi = RetrofitService.getInstance();
    }

    public MutableLiveData<WeatherResponse> getCurrentWeatherResponse(String cityName, String lat, String lon) {
        final MutableLiveData<WeatherResponse> weatherResponseMutableLiveData = new MutableLiveData<>();

        restApi.getCurrentWeather(cityName, lat, lon, "", "metric", "7da46d5eb582421b0b66792380bc4a88")
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Log.d(TAG, "onResponse: " + response.body().toString());
                                weatherResponseMutableLiveData.postValue(response.body());
                            } else {
                                weatherResponseMutableLiveData.postValue(null);

                            }
                        } else {
                            weatherResponseMutableLiveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: error", t);
                        weatherResponseMutableLiveData.postValue(null);

                    }
                });


        return weatherResponseMutableLiveData;
    }

}
