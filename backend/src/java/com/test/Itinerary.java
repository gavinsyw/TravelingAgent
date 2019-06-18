package com.test;

import java.util.ArrayList;
import java.util.List;

public class Itinerary {
    private int itinerary_id;
    private int user_id;
    private int city_id;

    private List<Spot> spotList;

    public Itinerary(int ID) {
        this.itinerary_id = ID;
        this.spotList = new ArrayList<>(10);
    }
    
    public Itinerary(int ID, int city_id, int user_id, List<Spot> list) {
        this.itinerary_id = ID;
        this.city_id = city_id;
        this.user_id = user_id;
        this.spotList = list;
    }

    public Itinerary(int ID, List<Spot> spotList) {
        this.itinerary_id = ID;
        this.spotList = spotList;
    }

    public boolean add(Spot nextSpot) {
        spotList.add(nextSpot);
        return true;
    }

    public int getSpotNum() {
        return spotList.size();
    }
    
    public List<Spot> getSpotList() {
        return spotList;
    }
    
    public int getItineraryID() {
        return itinerary_id;
    }
        
    public int getUserID() {
        return user_id;
    }
    
    public int getCityID() {
        return city_id;
    }
}
