import java.lang.*;


public class Sight {
	float longitude, latitude;
	public int popularity;
	public int feeling;
	public int menPiao;
	Integer timeCost;
	
	Sight(float longitude, float latitude, int popularity, int feeling, int menPiao, int timeCost) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.popularity = popularity;
		this.feeling = feeling;
		this.menPiao = menPiao;
		this.timeCost = new Integer(timeCost);
		return;
	}
	
	private float abs(float a) {
		if (a > 0) {
			return a;
		}
		else {
			return (-a);
		}
	}
	
	public float distance(Sight s) {
		return (abs(this.longitude - s.longitude) + abs(this.latitude - s.latitude));
	}
}
