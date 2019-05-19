package com.example.travelingagent.myclass;

public class Hotel extends Spot {
    Hotel(String name, int ID, int spotType, String description, double longitude, double latitude) {
        super(name, ID, spotType, description, longitude, latitude);
        // TODO Auto-generated constructor stub
    }

    public String getName() {
        return this.name;
    }
}
