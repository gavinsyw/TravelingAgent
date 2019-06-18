/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;
import java.util.*;
import java.lang.*;


public class Graph {
	int sightNum, hotelNum;
	Vector<Hotel> hotels;
	Vector<Sight> sights;
	
	public Graph(Vector<Sight> sights, Vector<Hotel> hotels) {
		this.sightNum = sights.size();
                this.hotelNum = hotels.size();
		this.sights = sights;
		this.hotels = hotels;
	}
	
	private double greedyNext(Spot currentSight, Vector<Spot> currentSequence, double distanceLoss, double popularityLoss, double scoreLoss, double environmentLoss, double serviceLoss, double costLoss) {
		double maxReward = -10000;
		Sight maxSight = sights.get(0);
		for (int i = 0; i < sightNum; ++i) {
			Sight trySight = sights.get(i);
			boolean existFlag = false;
			for (int j = 0; j < currentSequence.size(); ++j) {
				if (currentSequence.get(j).getID() == trySight.getID()) {
					existFlag = true;
                                        break;
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
                currentSequence.add(maxSight);
		return maxReward;
	}
	
	private Vector<Spot> greedy(int numberOfSightPerDay, double distanceLoss, double popularityLoss, double scoreLoss, double environmentLoss, double serviceLoss, double costLoss) {
		// initialize the travelSquence
		Integer currentIndex = 0;
                int maxReward = -100000;
                Vector<Spot> finalSequence = new Vector<Spot>(sightNum+hotelNum);
                for (int j = 0; j < hotelNum; ++j) {
                    Vector<Spot> travelSequence = new Vector<Spot>(sightNum+hotelNum);
                    int currentReward = -100000;
                    Hotel hotel = hotels.get(j);
                    for (int day = 0; day < sightNum/numberOfSightPerDay-1; ++day) {
			Spot currentSight = hotel;
			for (int i = 0; i < numberOfSightPerDay; ++i) {
				currentIndex += 1;
				currentReward += greedyNext(currentSight, travelSequence, distanceLoss, popularityLoss, scoreLoss, environmentLoss, serviceLoss, costLoss);
			}
			travelSequence.add(hotel);
                    }
                    currentReward += hotel.hotelReward(popularityLoss, scoreLoss, costLoss);
                    if (currentReward > maxReward) {
                        finalSequence = travelSequence;
                    }
                    currentReward = -100000;
                }
		
		return finalSequence;
	}
	
	public Vector<Spot> journeySequence(int numberOfSightPerDay, double distanceLoss, double popularityLoss, double scoreLoss, double environmentLoss, double serviceLoss, double costLoss) {
		return greedy(numberOfSightPerDay, distanceLoss, popularityLoss, scoreLoss, environmentLoss, serviceLoss, costLoss);
	}
}
