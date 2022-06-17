package com.example.Model;

public class Participant {
    private final String name;
    private final String username;
    private final String profilePic;

    public Participant(String name, String username, String profilePic) {
        this.name = name;
        this.username = username;
        this.profilePic = profilePic;
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
