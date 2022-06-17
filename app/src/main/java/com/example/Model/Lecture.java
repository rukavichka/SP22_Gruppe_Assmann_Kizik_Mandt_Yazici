package com.example.Model;

import java.util.ArrayList;

public class Lecture {
    private final String lectureName;
    private final String professorName;
    private final String lectureTime;
    private final String lectureSemester;
    private final String lectureRoom;
    private final String lectureContent;
    private final String lectureExam;


    private boolean isJoined; //needs to be adapted
    private final ArrayList<Participant> lectureParticipant;

    // Professor Object (Interface/Inheritance)

    public Lecture(String lectureName, String professorName, String lectureTime, String lectureSemester, String lectureRoom, String lectureContent, String lectureExam, boolean isJoined) {
        this.lectureName = lectureName;
        this.professorName = professorName;
        this.lectureTime = lectureTime;
        this.lectureSemester = lectureSemester;
        this.lectureRoom = lectureRoom;
        this.lectureContent = lectureContent;
        this.lectureExam = lectureExam;
        this.isJoined = isJoined;
        this.lectureParticipant = new ArrayList<>();
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

    public String getLectureSemester() {
        return lectureSemester;
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

    public ArrayList<Participant> getLectureParticipant() {
        return lectureParticipant;
    }

    public boolean isJoined() {
        return isJoined;
    }

    public void setJoined(boolean joined) {
        isJoined = joined;
    }

}
