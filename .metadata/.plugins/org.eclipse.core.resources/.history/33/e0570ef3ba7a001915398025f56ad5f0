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
}
