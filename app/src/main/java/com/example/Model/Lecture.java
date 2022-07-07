package com.example.Model;

import java.util.ArrayList;

public class Lecture {
    private final String lectureName;
    private final String form;
    private final String number;
    private final String professorName;
    private final String lectureTime;
    private final String semester;
    private final String lecturePeriod;
    private final String lectureRoom;
    private final String lectureContent;
    private final String lectureExam;
    private boolean isJoined;

    // Professor Object (Interface/Inheritance)

    public Lecture(String lectureName, String professorName, String lectureTime, String number, String form, String semester, String lectureRoom, String lectureContent, String lectureExam, String lecturePeriod) {
        this.lectureName = lectureName;
        this.professorName = professorName;
        this.lectureTime = lectureTime;
        this.lecturePeriod = lecturePeriod;
        this.semester = semester;
        this.lectureRoom = lectureRoom;
        this.lectureContent = lectureContent;
        this.lectureExam = lectureExam;
        this.form = form;
        this.number = number;
    }

    public String getLecturePeriod() {
        return lecturePeriod;
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public String getLectureTime() {
        return lectureTime;
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

    public String getLectureContent() {
        return lectureContent;
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


    public void setJoined(boolean joined){
        this.isJoined = joined;
    }
    // Further implementation needed
    public boolean isJoined() {
        return true;
    }
}
