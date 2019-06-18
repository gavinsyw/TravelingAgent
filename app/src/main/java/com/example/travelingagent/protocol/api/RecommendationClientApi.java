package com.example.travelingagent.protocol.api;

import com.example.travelingagent.entity.Spot;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RecommendationClientApi {
    @GET("jsf-helloworld/recom")
    Call<List<Spot>> recommend(@QueryMap Map<String, String> options);
}
