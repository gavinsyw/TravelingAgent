package com.example.travelingagent.protocol;

import com.example.travelingagent.myentity.Spot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CustomizationClientApi {
//    @GET("jsf-helloworld/login")
//    Call<Weather> loginState(@QueryMap Map<String, String> options);

//    @POST("jsf-helloworld/save_itinerary")
//    Call<Itinerary> itineraryAdd(@Body Itinerary itinerary);

    @GET("jsf-helloworld/simulation")
    Call<List<Spot>> spotLoad(@Query("city_id") String city_id);
}
