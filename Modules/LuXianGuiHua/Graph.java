import java.util.*;
import java.lang.*;

public class Graph {
	int sightNum;
	Sight hotel;
	Vector<Sight> sights;
	
	public Graph(int sightNum, Vector<Sight> sights, Sight hotel) {
		this.sightNum = sightNum;
		this.sights = sights;
		this.hotel = hotel;
	}
	
	private Sight greedyNext(Sight currentSight, Vector<Sight> currentSequence) {
		double maxReward = -10000;
		Sight maxSight = sights.get(0);
		for (int i = 0; i < sights.size(); ++i) {
			Sight trySight = sights.get(i);
			boolean existFlag = false;
			for (int j = 0; j < currentSequence.size(); ++j) {
				if (currentSequence.get(j).equals(trySight)) {
					existFlag = true;
				}
			}
			if (existFlag) {
				continue;
			}
			if (currentSight.reward(trySight) > maxReward) {
				maxSight = trySight;
				maxReward = currentSight.reward(trySight);
			}
		}
		maxSight.print();
		return maxSight;
	}
	
	private Vector<Sight> greedy(int numberOfSightPerDay) {
		Vector<Sight> travelSequence = new Vector<Sight>(sightNum);
		// initialize the travelSquence
		Integer currentIndex = 0;
		for (int day = 0; day < 10; ++day) {
			Sight currentSight = hotel;
			for (int i = 0; i < numberOfSightPerDay; ++i) {
				currentIndex += 1;
				currentSight = greedyNext(currentSight, travelSequence);
				travelSequence.add(currentSight);
			}
			travelSequence.add(hotel);
		}
		return travelSequence;
	}
	
	public Vector<Sight> journeySequence(int numberOfSightPerDay) {
		return greedy(numberOfSightPerDay);
	}
}
