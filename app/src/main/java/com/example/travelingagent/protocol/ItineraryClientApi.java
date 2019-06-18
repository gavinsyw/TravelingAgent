package com.example.travelingagent.protocol;

import com.example.travelingagent.activity.Itinerary;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ItineraryClientApi {
//    @GET("jsf-helloworld/login")
//    Call<Weather> loginState(@QueryMap Map<String, String> options);

    @POST("jsf-helloworld/save_itinerary")
    Call<Itinerary> itineraryAdd(@Body Itinerary itinerary);

    @GET("jsf-helloworld/send_itinerary")
    Call<List<Itinerary>> itinerariesLoad(@Query("user_id") String user_id);

    @GET("jsf-helloworld/get_itinerary")
    Call<Itinerary> itineraryGet(@QueryMap Map<String, String> options);
}
