package com.example.travelingagent.myclass;

import java.lang.*;


public class Sight extends Spot{
	protected double popularity;
	protected double score, environment, service;
	protected double menPiao;
	
	public Sight(String name, int ID, int spotType, String description, double longitude, double latitude, double popularity, double score, double environment, double service, double menPiao) {
		super(name, ID, spotType, description, longitude, latitude);
		this.popularity = popularity;
		this.score = score;
		this.environment = environment;
		this.service = service;
		this.menPiao = menPiao;
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
