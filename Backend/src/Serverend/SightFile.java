package Serverend;

import java.io.*;
import java.util.*;
import SupportClass.*;

public class SightFile {
	int sightNum;
	String cityName;
	
	public SightFile(String cityName, int sightNum) {
		this.sightNum = sightNum;
	}
	
	public void writeToFile(Vector<Sight> sightVec) throws IOException {
		File file = new File("src/resources/"+cityName+".txt");
		file.createNewFile();
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		
		String allData = "";
		
		for (int i = 0; i < sightNum; i++) {
			Sight s = sightVec.get(i);
			allData += s.info() + '\n';
		}
		
		raf.writeChars(allData);
		
		raf.close();
	}
	
	public Vector<Sight> sightVec() throws FileNotFoundException {
        File f = new File("src/resources/"+cityName+".txt");
        String sightName, description;
		int index, popularity, ticket, totalScore, environment, service;
		double longitude, latitude;
        Scanner sc = new Scanner(f, "GB2312");
		Vector<Sight> s = new Vector<Sight>(60);
		for (int i = 0; i < 60 && sc.hasNext(); ++i) {
			index = sc.nextInt();
			sightName = sc.next();
			description = sc.next();
			popularity = sc.nextInt();
			ticket = sc.nextInt();
			totalScore = sc.nextInt();
			environment = sc.nextInt();
			service = sc.nextInt();
			longitude = sc.nextDouble();
			latitude = sc.nextDouble();
			s.add(new Sight(sightName, index, 1, description, longitude, latitude, popularity, totalScore, environment, service, ticket));
		}
		return s;
    }
}