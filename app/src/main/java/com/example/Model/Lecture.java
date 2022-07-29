package com.example.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Lecture {
    private String lectureName;
    private String form;
    private String number;
    private String semester;
    private String professorName;
    private String lectureRoom;
    private String lectureExam;
    private boolean isJoined;
    private Schedule schedule;
    private HashMap<String, ArrayList<ArrayList<String>>> content;

    // Professor Object (Interface/Inheritance)

    public Lecture(String lectureName, String lecturePeriod, String lectureRoom, ArrayList<String> times){
        this.lectureName = lectureName;
        this.schedule = new Schedule(lecturePeriod);
        this.lectureRoom = lectureRoom;
        String[] temp;

        for(String time: times) {
            temp = time.split(" ");
            this.schedule.addTime(temp[0], temp[1]);
        }
    }

    public Lecture(String name, String professor, String semester, String number, String form, String room) {
        this.lectureName = name;
        this.professorName = professor;
        this.semester = semester;
        this.number = number;
        this.form = form;
        this.lectureRoom = room;
    }

    public Lecture(String lectureName, String professorName, String lectureTime, String number, String form, String semester, String lectureRoom, HashMap<String, ArrayList<ArrayList<String>>> content, String lectureExam, String lecturePeriod) {
        this.lectureName = lectureName;
        this.professorName = professorName;
        this.schedule = new Schedule(lecturePeriod);
        String temp[] = lectureTime.split(" ");
        this.schedule.addTime(temp[0], temp[1]);
        this.semester = semester;
        this.lectureRoom = lectureRoom;
        this.lectureExam = lectureExam;
        this.form = form;
        this.number = number;
        this.content = content;
    }

    public String getLecturePeriod() {
        return this.schedule.getPeriod();
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public ArrayList<String> getLectureTime() {
        return this.schedule.getHours();
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public String getLectureDate() {
        return "14/06/2022";
    }

    public String getSemester() {
        return semester;
    }

    public String getLectureRoom() {
        return lectureRoom;
    }

    public HashMap<String, ArrayList<ArrayList<String>>> getLectureContent() {
        // Type implementation needed
        return content;
    }

    public String getLectureExam() {
        return lectureExam;
    }

    public String getForm() {
        return form;
    }

    public String getNumber() {
        return number;
    }

    public boolean isBusy(Date date) {
        if(this.schedule.isInPeriod(date)) {
            if(this.schedule.isBusy(date.getDayOfWeek(), date.getTime())) {
                return true;
            }
        }
        return false;
    }

    public void addTime(String time) {
        String[] temp = time.split(" ");
        this.schedule.addTime(temp[0], temp[1]);
    }

    public void setJoined(boolean joined){
        this.isJoined = joined;
    }
    // Further implementation needed
    public boolean isJoined() {
        return true;
    }
}
