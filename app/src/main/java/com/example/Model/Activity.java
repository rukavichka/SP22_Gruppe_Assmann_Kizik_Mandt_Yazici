package com.example.Model;

public class Activity {
    private final String title;
    private final String date;
    private final String content;

    public Activity(String title, String date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDate() {
        return this.date;
    }

    public String getContent() {
        return this.content;
    }
}
