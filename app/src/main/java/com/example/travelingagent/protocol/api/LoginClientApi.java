package com.example.travelingagent.protocol.api;

import android.util.Log;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface LoginClientApi {
    @GET("jsf-helloworld/login")
    Call<String> loginState(@QueryMap Map<String, String> options);
}
