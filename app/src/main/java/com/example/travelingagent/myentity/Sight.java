package com.example.travelingagent.myentity;

import java.lang.*;


public class Sight extends Spot{
	protected double environment, service;

	public Sight(String name, int ID, int spotType, String description, double longitude, double latitude, double popularity, double total, double environment, double service, double money) {
		super(name, ID, spotType, description, longitude, latitude);
		this.popularity = popularity;
		this.environment = environment;
		this.service = service;

		super.setTotal(total);
		super.setPopularity(popularity);
		super.setMoney(money);
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
