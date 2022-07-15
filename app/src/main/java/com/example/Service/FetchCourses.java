package com.example.Service;

import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.Model.Lecture;
import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.FirebaseItem;
import com.example.View.FilterFragment;
import com.example.View.LectureDetailsPageFragment;
import com.example.View.LectureSearchPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public final class  FetchCourses extends AsyncTask<Integer, Void, Void> {
    private static final Firebase firebase = new Firebase();
    private String courseName;
    private static final HashMap<String, HashMap<String, String>> result = new HashMap<>();
    private static final ArrayList<String> resultCoursesTitles = new ArrayList<>(Arrays.asList(""));
    private static final ArrayList<String> resultCoursesForms = new ArrayList<>(Arrays.asList(""));
    private ArrayList<Lecture> info;
    private WeakReference<Fragment> weakReference;

    public FetchCourses() {
        super();
    }

    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    protected Void doInBackground(Integer... mode) {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(mode[0] == 0) {
                    setCourseSmallData(snapshot);
                    LectureSearchPageFragment temp = (LectureSearchPageFragment)weakReference.get();
                    temp.recyclerViewLecture(result);
                    temp.hideProgressBar();
                }
                else if(mode[0] == 1) {
                    info = new ArrayList<>();
                    LectureDetailsPageFragment temp = (LectureDetailsPageFragment)weakReference.get();
                    setCourseLargeData(snapshot);
                    temp.setCourseInfo(info);
                    temp.hideProgressBar();
                }
                else if(mode[0] == 2) {
                    LectureSearchPageFragment temp = (LectureSearchPageFragment)weakReference.get();
                    HashMap<String, String> filterparameters = temp.getFilterParameters();
                    System.out.println("Filterparams" + filterparameters);
                    setFilteredCourseSmallData(snapshot, filterparameters);
                    temp.hideProgressBar();
                    temp.recyclerViewLecture(result);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        if(mode[0] == 0) {
            firebase.getCourseDatabase("course-short-info").addValueEventListener(vListener);   //.limitToFirst(10).addValueEventListener(vListener);
        }
        else {
            firebase.getCourseDatabase("veranstaltungen").addValueEventListener(vListener);
        }
        return null;
    }



    public void setCourseSmallData(DataSnapshot snapshot) {
        result.clear();
        for(DataSnapshot ds:snapshot.getChildren()){
            HashMap<String, String> info = new HashMap<>();
            FirebaseItem item = ds.getValue(FirebaseItem.class);
            //System.out.println(item.getTitleSemabh());
            info.put("semester", item.getSemester());
            info.put("form", item.getForm());
            info.put("number", item.getLectureNum());
            info.put("prof", item.getRespLecturer());
            result.put(item.getTitleSemabh(), info);
        }
    }

    public void setFilteredCourseSmallData(DataSnapshot snapshot, HashMap<String, String> filterparameters) {
        result.clear();
        for(DataSnapshot ds:snapshot.getChildren()){
            HashMap<String, String> info = new HashMap<>();
            FirebaseItem item = ds.getValue(FirebaseItem.class);


            if (item.getTitleSemabh().equals(filterparameters.get("titleSemabh")) ||
                    item.getRespLecturer().equals(filterparameters.get("respLecturer")) ||
                    item.getSemester().equals(filterparameters.get("semester")) ||
                    item.getRoom().equals(filterparameters.get("room")) ||
                    item.getForm().equals(filterparameters.get("form"))) {

                info.put("semester", item.getSemester());
                info.put("form", item.getForm());
                info.put("number", item.getLectureNum());
                info.put("prof", item.getRespLecturer());
                result.put(item.getTitleSemabh(), info);
            }
            System.out.println(result);
        }
    }

    public void setCourseLargeData(DataSnapshot snapshot) {
        for(DataSnapshot ds:snapshot.getChildren()){
            FirebaseItem item = ds.getValue(FirebaseItem.class);
            Lecture lecture = new Lecture(courseName, item.getRespLecturer(), item.getWeekDay() + " " + item.getTimeFrom() + "-" + item.getTimeTill(), item.getLectureNum(), item.getForm(), item.getSemester(), item.getRoom(), "", "", item.getFrom() + "-" + item.getTill());
            info.add(lecture);
        }
    }

    public HashMap<String, HashMap<String, String>> getResult() {
        return result;
    }

    public ArrayList<Lecture> getInfo() {
        return info;
    }
}
