package com.example.travelingagent.protocol;

import com.example.travelingagent.entity.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherClientApi {
//    @GET("jsf-helloworld/login")
//    Call<Weather> loginState(@QueryMap Map<String, String> options);

    @GET("app/weather/listWeather")
    Call<Weather> weather(@Query("cityIds") String id);
}
