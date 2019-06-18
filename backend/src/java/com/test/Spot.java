/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

/**
 *
 * @author SJTUwwz
 */
import com.baidu.mapapi.model.LatLng;

import java.util.Date;

public class Spot {
    protected String name;
    protected int ID;
    protected int spotType;   // 0 for sight and 1 for hotel
    protected String description;
    protected double longitude, latitude,money,total,popularity;

    protected Spot(String name, int ID, int spotType, String description, double longitude, double latitude, double money, double total, double popularity) {
        this.name = name;
        this.ID = ID;
        this.spotType = spotType;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.popularity = popularity;
        this.money = money;
        this.total = total;
    }

    public double reward(Sight anoSpot, double distanceLoss, double popularityLoss, double scoreLoss, double environmentLoss, double serviceLoss, double costLoss) {
        double reward = -distanceLoss * 2 * this.distance(anoSpot) / 50;
        reward += popularityLoss * 3 * anoSpot.popularity / 3000;
        reward += scoreLoss * 3 * anoSpot.total / 50;
	reward += environmentLoss * 3 * anoSpot.environment / 50;
	reward += serviceLoss * 3 * anoSpot.service / 50;
	reward += -costLoss * 3 * anoSpot.money / 100;
	return reward;
    }

    protected double distance(Sight s) {
        double EARTH_RADIUS = 6371.0;
        double lon1 = Math.toRadians(this.longitude);
        double lon2 = Math.toRadians(s.longitude);
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(s.latitude);
        double vlon = abs(lon1 - lon2);
        double vlat = abs(lat1 - lat2);
        return EARTH_RADIUS * Math.acos(Math.sin(lon1)*Math.sin(lon2) + Math.cos(lon1)*Math.cos(lon2)*Math.cos(vlat));
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

}
