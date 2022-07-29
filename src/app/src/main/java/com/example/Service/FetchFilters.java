package com.example.Service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Model.Lecture;
import com.example.SoapAPI.Firebase;
import com.example.View.FilterFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FetchFilters extends AsyncTask<Void, Void, Void> {
    private final Firebase firebase = new Firebase();
    private ArrayList<String> professors;
    private ArrayList<String> semesters;
    private ArrayList<String> types;
    private ArrayList<String> titles;
    private ArrayList<Lecture> lectures;
    private WeakReference<Fragment> weakReference;
    public FetchFilters() {
        professors = new ArrayList<>();
        semesters = new ArrayList<>();
        types = new ArrayList<>();
        titles = new ArrayList<>();
    }

    public void setLectures(ArrayList<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if(lectures == null) {
            ValueEventListener vListener = new ValueEventListener() {

                /** gets invoked when attached or when data changes within Firebase
                 * @param snapshot current state of Firebase
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    setFilters(snapshot);
                    setFragment();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            };
            firebase.getCourseDatabase("filters").addValueEventListener(vListener);
        }
        else {
            setFiltersFromList();
            setFragment();
        }
        return null;
    }

    /** loads data from "filters" entry in Firebase to later set the spinner
     * @param snapshot current state of Firebase
     */
    private void setFilters(DataSnapshot snapshot) {
        professors = (ArrayList<String>) snapshot.child("professors").getValue();
        semesters = (ArrayList<String>) snapshot.child("semesters").getValue();
        titles = (ArrayList<String>) snapshot.child("titles").getValue();
        types = (ArrayList<String>) snapshot.child("types").getValue();

        professors.add(0, "");
        types.add(0, "");
        titles.add(0, "");
        semesters.add(0, "");
    }

    private void setFragment() {
        FilterFragment temp = (FilterFragment) weakReference.get();
        temp.setSpinnerCourse(titles);
        temp.setSpinnerCourseForm(types);
        temp.setSpinnerDozent(professors);
        temp.setSpinnerSemester();
    }

    private void setFiltersFromList() {
        professors.add("");
        types.add("");
        titles.add("");
        semesters.add("");

        for(Lecture lecture: lectures) {
            professors.add(lecture.getProfessorName());
            semesters.add(lecture.getSemester());
            titles.add(lecture.getLectureName());
            types.add(lecture.getForm());
        }
    }

    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }
}
