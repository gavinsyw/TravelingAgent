package com.example.lbstest.myclass;

public class Hotel extends Spot {
    private int ID;
    private String name;
    private double popularity;
    private double price;
    private double total;
    private double latitude;
    private double longitude;

    Hotel(int ID, String name, double popularity, double price, double total, double latitude, double longitude) {
        this.ID = ID;
        this.name = name;
        this.popularity = popularity;
        this.price = price;
        this.total = total;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return this.name;
    }
}
