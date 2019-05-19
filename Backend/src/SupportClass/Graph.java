package SupportClass;

import java.util.*;
import java.lang.*;

public class Graph {
	int sightNum;
	Hotel hotel;
	Vector<Sight> sights;
	
	public Graph(int sightNum, Vector<Sight> sights, Hotel hotel) {
		this.sightNum = sightNum;
		this.sights = sights;
		this.hotel = hotel;
	}
	
	private Sight greedyNext(Spot currentSight, Vector<Spot> currentSequence, double distanceLoss, double popularityLoss, double scoreLoss, double environmentLoss, double serviceLoss, double costLoss) {
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
			if (currentSight.reward(trySight, distanceLoss, popularityLoss, scoreLoss, environmentLoss, serviceLoss, costLoss) > maxReward) {
				maxSight = trySight;
				maxReward = currentSight.reward(trySight, distanceLoss, popularityLoss, scoreLoss, environmentLoss, serviceLoss, costLoss);
			}
		}
		maxSight.print();
		return maxSight;
	}
	
	private Vector<Spot> greedy(int numberOfSightPerDay, double distanceLoss, double popularityLoss, double scoreLoss, double environmentLoss, double serviceLoss, double costLoss) {
		Vector<Spot> travelSequence = new Vector<Spot>(sightNum);
		// initialize the travelSquence
		Integer currentIndex = 0;
		for (int day = 0; day < 10; ++day) {
			Spot currentSight = hotel;
			for (int i = 0; i < numberOfSightPerDay; ++i) {
				currentIndex += 1;
				currentSight = greedyNext(currentSight, travelSequence, distanceLoss, popularityLoss, scoreLoss, environmentLoss, serviceLoss, costLoss);
				travelSequence.add(currentSight);
			}
			travelSequence.add(hotel);
		}
		return travelSequence;
	}
	
	public Vector<Spot> journeySequence(int numberOfSightPerDay, double distanceLoss, double popularityLoss, double scoreLoss, double environmentLoss, double serviceLoss, double costLoss) {
		return greedy(numberOfSightPerDay, distanceLoss, popularityLoss, scoreLoss, environmentLoss, serviceLoss, costLoss);
	}
}
