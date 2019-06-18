package com.example.travelingagent.myclass;

import java.lang.*;
import java.util.*;
import java.io.*;

import android.content.Context;
import android.util.Log;

import com.example.travelingagent.R;

public class ReadFile {
	String city;
	Context context;

	ReadFile(String city) {
		this.city = city;
	}

	public Vector<Sight> sightVec() throws FileNotFoundException{
		File f = new File("sight_information.txt");
		InputStream in = context.getResources().openRawResource(R.raw.sight_information);

		if (f.isFile()) {
			Log.d("ReadFile", "true");
		}

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
			s.add(new Sight(sightName, index, 0, "balabalabala", longitude, latitude, popularity, totalScore, environment, service, ticket));
		}
		return s;
	}
}