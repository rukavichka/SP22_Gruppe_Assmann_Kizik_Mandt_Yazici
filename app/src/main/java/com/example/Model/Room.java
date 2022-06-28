package com.example.Model;

import android.location.Location;

public class Room {


    protected String roomNumber;
    protected double longitudeGEO;
    protected double latitudeGEO;
    protected String building;

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getBuilding() {
        return building;
    }
}
