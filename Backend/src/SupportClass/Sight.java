package SupportClass;

import java.lang.*;


public class Sight extends Spot{
	protected int popularity;
	protected int score, environment, service;
	protected int menPiao;
	
	public Sight(String name, int ID, int spotType, String description, double longitude, double latitude, int popularity, int score, int environment, int service, int menPiao) {
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
	
	public String info() {
		return this.name + '\t' + this.ID + '\t' + this.spotType + '\t' + this.description + '\t' + this.longitude + '\t' + this.latitude + '\t' + this.popularity + '\t'+ this.score + '\t' + this.environment + '\t' + this.service + '\t' + this.menPiao;
	}
}
