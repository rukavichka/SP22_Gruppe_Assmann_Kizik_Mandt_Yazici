package com.example.Model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomNumber;
    private String building;
    private ArrayList<Lecture> lectureList;

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
        this.lectureList = new ArrayList<>();
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getBuilding() {
        return building;
    }

    public void addLecture(Lecture lecture) {
        lectureList.add(lecture);
    }

    public Lecture getLecture(Date date) {
        for(Lecture lecture : lectureList) {
            if(lecture.isBusy(date)) {
                return lecture;
            }
        }
        return null;
    }

    public ArrayList<Lecture> getLectureList() {
        return lectureList;
    }

    public boolean isFree(Date date) {
        for(Lecture lecture:lectureList) {
            if(lecture.isBusy(date)) {
               return false;
            }
        }
        return true;
    }
}
