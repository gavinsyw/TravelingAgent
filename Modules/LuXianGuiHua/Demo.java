import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sightName;
		int index, popularity, ticket, totalScore, environment, service;
		double longitude, latitude;
		Scanner sc = new Scanner(System.in, "GB2312");
		Vector<Sight> s = new Vector<Sight>(60);
		for (int i = 0; i < 60 && sc.hasNext(); ++i) {
			index = sc.nextInt();
			sightName = sc.next();
			popularity = sc.nextInt();
			ticket = sc.nextInt();
			totalScore = sc.nextInt();
			environment = sc.nextInt();
			service = sc.nextInt();
			longitude = sc.nextDouble();
			latitude = sc.nextDouble();
			s.add(new Sight(sightName, longitude, latitude, popularity, totalScore, environment, service, ticket));
		}
		Sight hotel = new Sight("xxhotel", 31.05485, 121.5033, 0, 0, 0, 0, 0);
		Graph g = new Graph(60, s, hotel);
		Vector<Sight> v = g.journeySequence(4);
		for (int i = 0; i < v.size(); ++i) {
			System.out.println(s.indexOf(v.get(i)));
		}
	}

}
