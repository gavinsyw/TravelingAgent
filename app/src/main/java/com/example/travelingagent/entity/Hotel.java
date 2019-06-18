package com.example.travelingagent.entity;

public class Hotel extends Spot {
    public Hotel(String name, int ID, int spotType, double popularity, double money, double total, double longitude, double latitude) {
        super(name, ID, spotType, "blabla", longitude, latitude);
        // TODO Auto-generated constructor stub
        super.setTotal(total);
        super.setPopularity(popularity);
        super.setMoney(money);
    }
}
