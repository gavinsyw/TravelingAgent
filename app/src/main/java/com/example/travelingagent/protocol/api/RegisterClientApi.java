package com.example.travelingagent.protocol.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RegisterClientApi {
    @GET("jsf-helloworld/register")
    Call<String> registerState(@QueryMap Map<String, String> options);
}
