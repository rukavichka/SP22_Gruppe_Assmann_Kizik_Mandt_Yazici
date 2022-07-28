package com.example.Model;

import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 */
public class Date {
    private String date;
    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;
    private static HashMap<String, Integer> months;
    private static HashMap<String, String> days;
    static {
        months = new HashMap<>();
        months.put("Jan", 1);
        months.put("Feb", 2);
        months.put("Mar", 3);
        months.put("Apr", 4);
        months.put("May", 5);
        months.put("Jun", 6);
        months.put("Jul", 7);
        months.put("Aug", 8);
        months.put("Sep", 9);
        months.put("Oct", 10);
        months.put("Nov", 11);
        months.put("Dec", 12);
    }

    public Date(String[] date) {
        this.day = Integer.parseInt(date[0]);
        this.month = Integer.parseInt(date[1]);
        this.year = Integer.parseInt(date[2]);
    }

    public Date(LocalDate date) {
        this.day = date.getDayOfMonth();
        this.month = date.getMonthValue();
        this.year = date.getYear();
    }

    public Date(String date) {
        this.date = date;
        convert();
    }

    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String getTime() {
        return this.hour + ":" + this.minute;
    }

    public String getDay() {
        LocalDate localDate = LocalDate.of(year, month, day);
        return localDate.getDayOfWeek().toString().substring(0, 3);
    }

    private void convert() {
        this.date = date.replace(",", "");
        String[] temp = date.split(" ");
        day = Integer.parseInt(temp[1]);
        year = Integer.parseInt(temp[2]);
        month = months.get(temp[0]);
    }

    public int compareTime(Date cDate) {
        if(this.hour > cDate.hour) {
            return 1;
        }
        else if(cDate.hour > this.hour) {
            return -1;
        }
        else {
            if(this.minute > cDate.minute) {
                return 1;
            }
            else if(cDate.minute > this.minute) {
                return -1;
            }
            return 0;
        }
    }

    /**
     * Compares 2 Date objects
     * Returns 1 if the current object is after the given object
     * Return 0 if 2 objects have the same date
     * Return -1 if the given object is after the current object
     */
    public int compare(Date cDate) {
        if(this.year > cDate.year) {
            return 1;
        }
        else if (cDate.year > this.year){
            return -1;
        }
        else {
            if(this.month > cDate.month) {
                return 1;
            }
            else if(cDate.month > this.month) {
                return -1;
            }
            else {
                if(this.day > cDate.day) {
                    return 1;
                }
                else if (cDate.day > this.day) {
                    return -1;
                }
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        String strDay = "" + day;
        String strMonth = "" + month;
        if(day < 10) strDay = "0" + day;
        if(month < 10) strMonth = "0" + month;
        return strDay + "." + strMonth + "." + year;
    }
}
