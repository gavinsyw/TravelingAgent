package com.example.travelingagent.protocol;

import com.example.travelingagent.myclass.Spot;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RecommendationClientApi {
//    @GET("jsf-helloworld/login")
//    Call<Weather> loginState(@QueryMap Map<String, String> options);

    @GET("jsf-helloworld/recom")
    Call<List<Spot>> recommend(@QueryMap Map<String, String> options);
}
