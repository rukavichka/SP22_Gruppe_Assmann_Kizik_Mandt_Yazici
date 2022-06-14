package com.example.Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Course {
    protected String lecture_ID;
    protected String name;
    protected String teacher;
    protected String semester;
    protected List<String> courseMeetings;

    protected boolean joined;



    public String getLecture_ID() {
        return lecture_ID;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getSemester() {
        return semester;
    }

    public List<String> getCourseMeetings() {
        return courseMeetings;
    }

    public boolean isJoined() {
        return joined;
    }

}
