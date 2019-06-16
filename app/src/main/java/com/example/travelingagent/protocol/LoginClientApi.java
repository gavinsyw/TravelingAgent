package com.example.travelingagent.protocol;

import android.util.Log;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface LoginClientApi {
    @GET("jsf-helloworld/login")
    Call<String> loginState(@QueryMap Map<String, String> options);

//    @GET("app/weather/listWeather")
//    Call<Weather> weather(@Query("cityIds") String id);
}
