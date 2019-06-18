package com.example.travelingagent.protocol.entity;

public class ModeEntity {
    private String name;
    private int imageID;

    public ModeEntity(String name, int imageID) {
        this.name = name;
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public int getImageID() {
        return imageID;
    }

}
