import java.util.*;
import java.lang.*;

public class Graph {
	int sightNum;
	Vector<Sight> sights;
	Vector<Integer> distance;
	
	public Graph(int sightNum, Vector<Sight> sights, Vector<Integer> distance) {
		this.sightNum = sightNum;
		this.sights = sights;
		this.distance = distance;
	}
	
	public Vector<Integer> journeySequence() {
		Vector<Integer> x = new Vector<Integer>(sightNum);
		for (int i = 0; i < sightNum; ++i) {
			x.add(i);
		}
		return x;
	}
}
