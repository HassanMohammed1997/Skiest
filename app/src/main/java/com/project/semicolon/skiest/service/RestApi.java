package com.project.semicolon.skiest.service;

import com.project.semicolon.skiest.model.response.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("weather")
    Call<WeatherResponse> getCurrentWeather(@Query("q") String query,
                                            @Query("lat") String lat,
                                            @Query("lon") String lon,
                                            @Query("id") String cityId,
                                            @Query("units") String units,
                                            @Query("appid") String key);


}
