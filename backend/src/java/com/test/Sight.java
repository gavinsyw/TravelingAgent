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
import java.lang.*;


public class Sight extends Spot{
	protected double environment, service;
	
	public Sight(String name, int ID, int spotType, String description, double longitude, double latitude, double popularity, double total, double environment, double service, double money) {
		super(name, ID, spotType, description, longitude, latitude, money, total, popularity);
		this.popularity = popularity;
		this.total = total;
		this.environment = environment;
		this.service = service;
		this.money = money;
	}
	
	public void print()	{
		System.out.println(this.name);
	}
	
	public boolean equals(Sight s) {
		if (this.popularity == s.popularity) {
			return true;
		}
		else {
			return false;
		}
	}
}
