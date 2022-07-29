package com.example.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Schedule {
    private Date startPeriod;
    private Date endPeriod;
    /*
     * HashMap<String, String> times format
     * Key: String -> The name of the day such as Mon, Di, Mit
     * Value: String -> The start/end time in the given day such as 14:00-16:00
     */
    private HashMap<String, String[]> times;

    public Schedule(String period) {
        String[] temp = period.split("-");
        if(temp[0].equals(" ") && temp[1].equals(" ")) {
            this.startPeriod = null;
            this.endPeriod = null;
        }
        else {
            this.startPeriod = new Date(temp[0].split("\\."));
            this.endPeriod = new Date(temp[1].split("\\."));
        }

        this.times = new HashMap<>();
    }

    public void addTime(String day, String time) {
        if(!times.containsKey(day) && !time.isEmpty()) {
            times.put(day, time.split("-"));
        }
    }

    public boolean isInPeriod(Date date) {
        return startPeriod.compare(date) < 1 && endPeriod.compare(date) > -1;
    }

    public boolean isBusy(String day, String cTime) {
        /*
         * Format of any time in String format is HH:MM
         * If it includes 2 different times then HH:MM-HH:MM
         */
        String[] temp = cTime.split(":");
        String[] startTime;
        String[] endTime;
        if(times.containsKey(day)) {
            startTime = Objects.requireNonNull(times.get(day))[0].split(":");
            endTime = Objects.requireNonNull(times.get(day))[1].split(":");
        }
        else {
            return false;
        }

        /*
         * Comparing the hours is enough for now
         * The availability of the rooms are shown by hours, minute is not included
         * TODO: Add minute implementation
         */
        if(Integer.parseInt(temp[0]) >= Integer.parseInt(startTime[0])) {
            if(Integer.parseInt(temp[0]) <= Integer.parseInt(endTime[0])) {
                return true;
            }
        }
        return false;
    }

    public String getPeriod() {
        if(startPeriod == null && endPeriod == null) {
            return "Not specified";
        }
        return startPeriod.toString() + "-" + endPeriod.toString();
    }

    public ArrayList<String> getHours() {
        ArrayList<String> result = new ArrayList<>();
        if(!times.isEmpty()) {
            for(String key : times.keySet()){
                result.add(key + " " + times.get(key)[0] + "-" + times.get(key)[1]);
            }
        }
        return result;
    }

    public HashMap<String, String[]> getTimes() {
        return this.times;
    }

    public boolean equals(String period) {
        return false;
    }
}
