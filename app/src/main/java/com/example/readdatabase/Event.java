package com.example.readdatabase;

public class Event {
    public String id, name, secondName, email;

    // one empty constructor
    public Event() {
    }

    public Event(String id, String name, String secondName, String email) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.email = email;
    }
}
