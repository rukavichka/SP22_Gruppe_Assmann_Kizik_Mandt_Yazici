package com.example.Model;

import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.MyCallback;

import java.util.HashMap;

public class AccessFirebase {

    Firebase fb = new Firebase();

    public void showAllCourses(MyCallback callback){
        fb.showCourses(callback);
    }

    public void filterCourses(MyCallback callback, HashMap<String, String> filterparameters){
        fb.filterCourses(callback, filterparameters);
    }

    public void showCourseDetails(MyCallback callback, String courseTitle){
        fb.showCourseDetails(callback, courseTitle);
    }
}
