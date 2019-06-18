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

public class Hotel extends Spot {
    
    
    public Hotel(String name, int ID, int spotType, double popularity, double money, double total, double longitude, double latitude, String description) {
        super(name, ID, spotType, description, longitude, latitude, money, total, popularity);
        this.popularity = popularity;
        this.total = total;
        this.money = money;
        // TODO Auto-generated constructor stub
    }

//    Hotel(String name, int hotid, int i, double popularity, double price, double total, double latitude, double longitude) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    public String getName() {
        return this.name;
    }
    
    public double hotelReward(double popularityLoss, double scoreLoss, double costLoss) {
        return popularity / 4000 * popularityLoss + (total - 4) * scoreLoss - (money / 20) * costLoss;
    }
}