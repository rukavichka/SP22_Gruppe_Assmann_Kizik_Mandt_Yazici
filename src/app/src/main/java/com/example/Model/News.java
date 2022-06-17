package com.example.Model;

public class News {
    private final String title;
    private final String date;
    private final String content;

    public News(String title, String date, String content) {
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
