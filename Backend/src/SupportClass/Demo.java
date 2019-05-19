package SupportClass;

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
			s.add(new Sight(sightName, 1, 1, "balabalabala", longitude, latitude, popularity, totalScore, environment, service, ticket));
		}
		Hotel hotel = new Hotel("xxhotel", 1, 0,"balabalabala", 31.05485, 121.5033, 1, 0, 1);
		Graph g = new Graph(60, s, hotel);
		double distanceLoss = 1, popularityLoss = 1, scoreLoss = 1, environmentLoss = 1, serviceLoss = 1, costLoss = 1;
		Vector<Spot> v = g.journeySequence(4, distanceLoss, popularityLoss, scoreLoss, environmentLoss, serviceLoss, costLoss);
		for (int i = 0; i < v.size(); ++i) {
			System.out.println(s.indexOf(v.get(i)));
		}
	}

}

