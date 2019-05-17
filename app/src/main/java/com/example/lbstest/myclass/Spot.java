package com.example.lbstest.myclass;

import java.util.Date;

public class Spot {
    protected String name;
    protected int ID;
    protected int spotType;   // 0 for sight and 1 for hotel
    protected String description;
    protected double longitude, latitude;

    Spot(String name, int ID, int spotType, String description, double longitude, double latitude) {
        this.name = name;
        this.ID = ID;
        this.spotType = spotType;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double reward(Sight anoSpot, double distanceLoss, double popularityLoss, double scoreLoss, double environmentLoss, double serviceLoss, double costLoss) {
        double reward = distanceLoss * this.distance(anoSpot);
        reward += popularityLoss * anoSpot.popularity;
		reward += scoreLoss * anoSpot.score;
		reward += environmentLoss * anoSpot.environment;
		reward += serviceLoss * anoSpot.service;
		reward += costLoss * anoSpot.menPiao;
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

}
