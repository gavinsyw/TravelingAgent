package com.example.travelingagent.myclass;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Demo {

	public static void main(String[] args) {
		Recommend r = new Recommend(3, 0, 0, 0, 0, 0, 0);
		List<Spot> s = r.recommend("Shanghai", new Hotel("FakeHotel", 1, 0, "Fake", 121.72, 31.55));
		for (int i = 0; i < s.size(); ++i) {
			System.out.println(s.get(i).name);
		}
	}

}

