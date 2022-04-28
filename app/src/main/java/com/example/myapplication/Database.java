package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Database {

    List<Userprofile> users;

    private static Database instance;

    public static synchronized Database getInstance(){
        if(instance == null){
            instance = new Database(new ArrayList<>());
        }
        return instance;
    }

    private Database(List<Userprofile> users) {
        this.users = users;
    }

    public void add(String username, String password){
        users.add(new Userprofile(username,password));
    }

    public boolean contains(String username, String password){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(username)){
                if (users.get(i).getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }


    public void setPassword(String username, String password){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(username)){
                users.get(i).setPassword(password);
            }
        }
    }

    public static class Userprofile{

        private String username;
        private String password;

        public Userprofile(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
