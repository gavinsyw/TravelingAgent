package com.example.travelingagent.entity;

import com.baidu.mapapi.model.LatLng;

public class Spot {
    protected String name;
    protected int ID;
    protected int spotType;   // 0 for sight and 1 for hotel
    protected String description;
    protected double longitude, latitude;
    protected int wordCloudResourceID;
    protected double total;
    protected double popularity;
    protected double money;
    protected int iconResourceID;

    protected Spot(String name, int ID, int spotType, String description, double longitude, double latitude) {
        this.name = name;
        this.ID = ID;
        this.spotType = spotType;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double reward(Sight anoSpot, double distanceLoss, double popularityLoss, double scoreLoss, double environmentLoss, double serviceLoss, double costLoss) {
        double reward = distanceLoss * this.distance(anoSpot);
        reward += popularityLoss * popularity;
		reward += scoreLoss * total;
		reward += environmentLoss * anoSpot.environment;
		reward += serviceLoss * anoSpot.service;
		reward += costLoss * money;
		return reward;
    }

    protected double distance(Sight s) {
		return (abs(this.longitude - s.longitude) + abs(this.latitude - s.latitude));
    }

    private double abs(double a) {
        if (a > 0) {
            return a;
        }
        else {
            return -a;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.spotType;
    }

    public LatLng getLatLng() {
        return new LatLng(this.latitude, this.longitude);
    }

    public int getID() {
        return this.ID;
    }

    public boolean setWordCloudResourceID(int drawResourceID) {
        this.wordCloudResourceID = drawResourceID;
        return true;
    }

    public int getWordCloudResourceID() {
        return this.wordCloudResourceID;
    }

    public boolean setIconResourceID(int drawResourceID) {
        this.iconResourceID = drawResourceID;
        return true;
    }

    public int getIconResourceID() {
        return this.iconResourceID;
    }

    public boolean setTotal(double total) {
        this.total = total;
        return true;
    }

    public double getTotal() {
        return total;
    }

    public boolean setPopularity(double popularity) {
        this.popularity = popularity;
        return true;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public boolean setMoney(double money) {
        this.money = money;
        return true;
    }

    public double getMoney() {
        return this.money;
    }

    public String getDescription() {
        return this.description;
    }
}
