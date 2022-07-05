package com.example.Model;

public class Participant {
    private String name;
    private String username;
    private String profilePic;
    private final String user_id;

    public Participant(String name, String username, String profilePic, String user_id) {
        this.name = name;
        this.username = username;
        this.profilePic = profilePic;
        this.user_id = user_id;
    }

    public Participant(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
