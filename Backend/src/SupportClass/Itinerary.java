package SupportClass;

import java.util.ArrayList;

public class Itinerary {
    private int ID;
    private ArrayList<Spot> spotList;

    Itinerary(int ID, Spot start) {
        this.ID = ID;
        this.spotList.add(start);
    }

    public int getSpotNum() {
        return spotList.size();
    }

    public boolean move(Spot nextSpot) {
        spotList.add(nextSpot);
        return true;
    }


}
