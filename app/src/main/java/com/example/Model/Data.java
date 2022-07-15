package com.example.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    private final static Data instance = new Data();
    private ArrayList<Lecture> lectures;
    private ArrayList<Room> rooms;
    private HashMap<String, ArrayList<String>> filters;

    private Data(){
        this.lectures = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.filters = new HashMap<>();
    }

    public static Data getInstance() {
        return instance;
    }

    public void addFilters(String key, ArrayList<String> value) {
        this.filters.put(key, value);
    }

    public ArrayList<String> getFilters(String key) {
        return this.filters.get(key);
    }

    public void setLectures(ArrayList<Lecture> lectures) {
        this.lectures = lectures;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void addLecture(Lecture lecture) {
        if(!lectures.contains(lecture)) {
            lectures.add(lecture);
        }
    }

    public void addRoom(Room room) {
        if(!rooms.contains(room)) {
            rooms.add(room);
        }
    }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public ArrayList<Lecture> getLectures(String name) {
        ArrayList<Lecture> result = new ArrayList<>();
        for(Lecture lecture: lectures) {
            if(lecture.getLectureName().equals(name)) {
                result.add(lecture);
            }
        }
        return result;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
