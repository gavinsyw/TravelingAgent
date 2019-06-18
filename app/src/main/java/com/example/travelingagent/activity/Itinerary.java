package com.example.travelingagent.activity;

import android.view.View;

import com.example.travelingagent.entity.Spot;

import java.util.ArrayList;
import java.util.List;

public class Itinerary {
    private int itinerary_id;
    private int user_id;
    private int city_id;
    private View.OnClickListener requestBtnClickListener;

    private List<Spot> spotList;

    public Itinerary(int ID, int city_id, int user_id) {
        this.itinerary_id = ID;
        this.spotList = new ArrayList<>(10);
        this.user_id = user_id;
        this.city_id = city_id;
    }

    public Itinerary(int ID, List<Spot> spotList, int city_id, int user_id) {
        this.itinerary_id = ID;
        this.spotList = spotList;
        this.city_id = city_id;
        this.user_id = user_id;
    }

    public boolean add(Spot nextSpot) {
        spotList.add(nextSpot);
        return true;
    }

    public int getSpotNum() {
        return spotList.size();
    }

    public int getCity_id() {
        return this.city_id;
    }

    public String getCityName() {
        switch (city_id) {
            case 1:
                return "北京";
            case 2:
                return "上海";
            case 3:
                return "广州";
            case 4:
                return "南京";
            case 5:
                return "苏州";
            case 6:
                return "杭州";
            case 7:
                return "武汉";
            case 8:
                return "长沙";
            case 9:
                return "西安";
            case 10:
                return "扬州";
            case 11:
                return "九寨沟";
            case 12:
                return "张家界";
            default:
                return "Unknown";
        }
    }

    public List<Spot> getSpotList() {
        return this.spotList;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }
}
