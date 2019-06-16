package com.example.travelingagent.myclass;

import java.util.ArrayList;
import java.util.List;

public class Itinerary {
    private int ID;
    private List<Spot> spotList;

    public Itinerary(int ID) {
        this.ID = ID;
        this.spotList = new ArrayList<>(10);
    }

    public boolean add(Spot nextSpot) {
        spotList.add(nextSpot);
        return true;
    }

    public int getSpotNum() {
        return spotList.size();
    }
}
