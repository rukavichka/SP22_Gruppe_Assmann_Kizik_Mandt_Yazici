package com.example.Model;

public class Participant {
    private String name;
    private String iliasUsername;
    private String profilePic;
    private String user_id;

    public Participant(String name, String username, String profilePic, String user_id) {
        this.name = name;
        this.iliasUsername = username;
        this.profilePic = profilePic;
        this.user_id = user_id;
    }

    public Participant(String name, String username, String user_id) {
        this.name = name;
        this.iliasUsername = username;
        this.user_id = user_id;
    }

    public Participant(String user_id) {
        this.user_id = user_id;
    }

    public Participant() {
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getIliasUsername() {
        return iliasUsername;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
