package com.example.lbstest.myclass;

import java.lang.*;
import java.util.*;

public class Recommend {
    int numberOfSightsPerDay;
    int choice1, choice2, choice3, choice4;

    Recommend(int numberOfSightsPerDay, int choice1, int choice2, int choice3, int choice4) {
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.numberOfSightsPerDay = numberOfSightsPerDay;
    }

    public List<Spot> recommend(String city, Hotel hotel) {
        Vector<Sight> sightVec = new Vector<Sight>(60);
        ReadFile f = new ReadFile("Shanghai");
        double distanceLoss = 1 + choice1;
        double popularityLoss = 1 + choice2;
        double scoreLoss = 1 + choice3;
        double environmentLoss = 1 + choice4;
        double serviceLoss = 1;
        double costLoss = 1;
        List<Spot> l = new Vector<Spot>(60);
        Graph g = new Graph(60, sightVec, hotel);
        Vector<Spot> s = g.journeySequence(this.numberOfSightsPerDay, distanceLoss, popularityLoss, scoreLoss, environmentLoss, serviceLoss, costLoss);
        for (int i = 0; i < s.size(); ++i) {
            l.add(s.get(i));
        }
        return l;
    }


}