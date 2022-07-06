package com.example.Model;

public class User {
    private String name;
    private long user_id;
    private String iliasUser;

    public User(String name, long user_id, String iliasUser) {
        this.name = name;
        this.user_id = user_id;
        this.iliasUser = iliasUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getIliasUser() {
        return iliasUser;
    }

    public void setIliasUser(String iliasUser) {
        this.iliasUser = iliasUser;
    }
}
