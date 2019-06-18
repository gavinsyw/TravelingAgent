package com.example.travelingagent.protocol.api;

import com.example.travelingagent.entity.Spot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CustomizationClientApi {
    @GET("jsf-helloworld/simulation")
    Call<List<Spot>> spotLoad(@Query("city_id") String city_id);
}
