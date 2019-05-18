package sqlite;

public class Hotel {
	double longitude, latitude;
	int popularity;
	int score;
	int menPiao;
	String name;
	int id;
	
	Hotel(int id, String name, double longitude, double latitude, int popularity, int score, int menPiao) {
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.popularity = popularity;
		this.score = score;
		this.menPiao = menPiao;
	}
	
	private double abs(double a) {
		if (a > 0) {
			return a;
		}
		else {
			return (-a);
		}
	}
	
	private double distance(Sight s) {
		return (abs(this.longitude - s.longitude) + abs(this.latitude - s.latitude));
	}
	
	public double reward(Sight s, double distanceLoss, double popularityLoss, double scoreLoss, double environmentLoss, double serviceLoss, double costLoss) {
		double reward = 0;
		reward -= distanceLoss * this.distance(s);
		reward += popularityLoss * s.popularity;
		reward += scoreLoss * s.score;
		reward += environmentLoss * s.environment;
		reward += serviceLoss * s.service;
		reward += costLoss * s.menPiao;
		return reward;
	}
	
	public void print()	{
		System.out.println(name);
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
