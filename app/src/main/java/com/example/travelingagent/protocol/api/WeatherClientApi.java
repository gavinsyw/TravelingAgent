package com.example.travelingagent.protocol.api;

import com.example.travelingagent.protocol.entity.WeatherEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherClientApi {
    @GET("app/weather/listWeather")
    Call<WeatherEntity> weather(@Query("cityIds") String id);
}
