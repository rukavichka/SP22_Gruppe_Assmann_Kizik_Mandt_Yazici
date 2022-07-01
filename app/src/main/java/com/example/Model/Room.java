package com.example.Model;

import android.location.Location;

import java.util.List;

public class Room {


    protected String roomNumber;
    protected double longitudeGEO;
    protected double latitudeGEO;
    protected String building;

    protected List<Lecture> lectureList;
    protected List<String> lectureStringList;

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getBuilding() {
        return building;
    }

    public void setLectureList(List<Lecture> list){
        this.lectureList = list;
    }

    public void setLectureStringList(List<String> list){
        this.lectureStringList = list;
    }
}
