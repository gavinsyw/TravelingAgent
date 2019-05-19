package com.example.lbstest.myclass;

import java.lang.*;
import java.util.*;
import java.io.*;

public class ReadFile {
    String city;

    ReadFile(String city) {
        this.city = city;
    }

    public Vector<Sight> sightVec() {
        File f = new File("./sight_infomation.txt");
        String sightName;
		int index, popularity, ticket, totalScore, environment, service;
		double longitude, latitude;
        Scanner sc = new Scanner(f, "GB2312");
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
    }
}