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
                    temp.hideProgressBar();
                    temp.recyclerViewLecture(result);
                }
                else if(mode[0] == 1) {
                    info = new ArrayList<>();
                    LectureDetailsPageFragment temp = (LectureDetailsPageFragment)weakReference.get();
                    setCourseLargeData(snapshot);
                    temp.hideProgressBar();
                    temp.setCourseInfo(info);
                }
                else if(mode[0] == 2) {
                    setCourseList(snapshot);
                    FilterFragment temp = (FilterFragment)weakReference.get();
                    temp.setSpinnerCourse(resultCoursesTitles);
                }
                else if(mode[0] == 3) {
                    setCourseFormsList(snapshot);
                    FilterFragment temp = (FilterFragment)weakReference.get();
                    temp.setSpinnerCourseForm(resultCoursesForms);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        if(mode[0] == 0) {
            firebase.getCourseDatabase("course-short-info").addValueEventListener(vListener);   //.limitToFirst(10).addValueEventListener(vListener);
        }
        else if(mode[0] == 1) {
            firebase.getCourseDatabase("veranstaltungen").orderByChild("titleSemabh").equalTo(courseName).addValueEventListener(vListener);
        }
        else if(mode[0] == 2 || mode[0] == 3) {
            firebase.getCourseDatabase("veranstaltungen").addValueEventListener(vListener);
        }
        return null;
    }

    /**
     * to get the courses titles as a list from Firebase without duplicates
     * @param snapshot
     */
    private void setCourseList(DataSnapshot snapshot) {
        for(DataSnapshot ds:snapshot.getChildren()){
            String title = ds.child("titleSemabh").getValue(String.class);
            if (!resultCoursesTitles.contains(title)) {
                resultCoursesTitles.add(title);
            }
        }
    }

    /**
     * to get the courses forms as a list from Firebase without duplicates
     * @param snapshot
     */
    private void setCourseFormsList(DataSnapshot snapshot) {
        for(DataSnapshot ds:snapshot.getChildren()){
            String form = ds.child("form").getValue(String.class);
            if (!resultCoursesForms.contains(form)) {
                resultCoursesForms.add(form);
            }
        }
    }

    private void setCourseSmallData(DataSnapshot snapshot) {
        int counter = 0;
        for(DataSnapshot ds:snapshot.getChildren()){
            System.out.println(counter++);
            HashMap<String, String> info = new HashMap<>();
            FirebaseItem item = ds.getValue(FirebaseItem.class);
            info.put("semester", item.getSemester());
            info.put("form", item.getForm());
            info.put("number", item.getLectureNum());
            info.put("prof", item.getRespLecturer());
            result.put(item.getTitleSemabh(), info);
        }
    }

    private void setCourseLargeData(DataSnapshot snapshot) {
        for(DataSnapshot ds:snapshot.getChildren()){
            FirebaseItem item = ds.getValue(FirebaseItem.class);
            Lecture lecture = new Lecture(courseName, item.getRespLecturer(), item.getTimeFrom() + " - " + item.getTimeTill(), item.getLectureNum(), item.getForm(), item.getSemester(), item.getRoom(), "", "", item.getFrom() + " - " + item.getTill());
            info.add(lecture);
        }
    }

    static public HashMap<String, HashMap<String, String>> getResult() {
        return result;
    }
}
