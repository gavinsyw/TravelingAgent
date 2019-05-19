package SupportClass;

public class Hotel extends Spot {
	int popularity, score, price;
    public Hotel(String name, int ID, int spotType, String description, double longitude, double latitude, int popularity, int score, int price) {
        super(name, ID, spotType, description, longitude, latitude);
        // TODO Auto-generated constructor stub
        this.popularity = popularity;
        this.score = score;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }
}
