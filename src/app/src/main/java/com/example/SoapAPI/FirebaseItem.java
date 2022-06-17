package com.example.SoapAPI;

import java.util.ArrayList;

public class FirebaseItem {

    private String till;              // Date
    private String from;              // Date
    private String form;
    private ArrayList<String> abbrevCode;
    private ArrayList<String> abbrevNumber;
    private String pg;
    private String room;
    private String rhythm;                  // enum
    private String semester;
    private ArrayList<String> stg;                 // ArrayList<String>
    private String weekDay;
    private String titleSemabh;
    private String titleSemunabh;
    private String lectureNum;
    private String respLecturer;
    private String timeFrom;          // LocalTime
    private String timeTill;          // LocalTime

    public FirebaseItem(){}

    public FirebaseItem(String till, String from, String form, ArrayList<String> abbrevNumber,
                        ArrayList<String> abbrevCode, String pg, String room, String rhythm, String semester,
                        ArrayList<String> stg, String weekDay, String titleSemabh, String titleSemunabh,
                        String lectureNum, String respLecturer, String timeFrom,
                        String timeTill) {
        this.till = till;
        this.from = from;
        this.form = form;
        this.abbrevNumber = abbrevNumber;
        this.abbrevCode = abbrevCode;
        this.pg = pg;
        this.room = room;
        this.rhythm = rhythm;                  // enum
        this.semester = semester;
        this.stg = stg;
        this.weekDay = weekDay;
        this.titleSemabh = titleSemabh;
        this.titleSemunabh = titleSemunabh;
        this.lectureNum = lectureNum;
        this.respLecturer = respLecturer;
        this.timeFrom = timeFrom;
        this.timeTill = timeTill;
    }


    public String getTill() {
        return till;
    }

    public void setTill(String till) {
        this.till = till;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public ArrayList<String> getAbbrevCode() {
        return abbrevCode;
    }

    public void setAbbrevCode(ArrayList<String> abbrevCode) {
        this.abbrevCode = abbrevCode;
    }

    public ArrayList<String> getAbbrevNumber() {
        return abbrevNumber;
    }

    public void setAbbrevNumber(ArrayList<String> abbrevNumber) {
        this.abbrevNumber = abbrevNumber;
    }

    public String getPg() {
        return pg;
    }

    public void setPg(String pg) {
        this.pg = pg;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRhythm() {
        return rhythm;
    }

    public void setRhythm(String rhythm) {
        this.rhythm = rhythm;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public ArrayList<String> getStg() {
        return stg;
    }

    public void setStg(ArrayList<String> stg) {
        this.stg = stg;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getTitleSemabh() {
        return titleSemabh;
    }

    public void setTitleSemabh(String titleSemabh) {
        this.titleSemabh = titleSemabh;
    }

    public String getTitleSemunabh() {
        return titleSemunabh;
    }

    public void setTitleSemunabh(String titleSemunabh) {
        this.titleSemunabh = titleSemunabh;
    }

    public String getLectureNum() {
        return lectureNum;
    }

    public void setLectureNum(String lectureNum) {
        this.lectureNum = lectureNum;
    }

    public String getRespLecturer() {
        return respLecturer;
    }

    public void setRespLecturer(String respLecturer) {
        this.respLecturer = respLecturer;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTill() {
        return timeTill;
    }

    public void setTimeTill(String timeTill) {
        this.timeTill = timeTill;
    }

    public String toString() {
        return titleSemabh + semester;
    }
}
