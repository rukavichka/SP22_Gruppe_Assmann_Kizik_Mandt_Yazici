package com.example.Service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
    private ArrayList<String> rooms;
    private ArrayList<String> semesters;
    private ArrayList<String> types;
    private ArrayList<String> titles;
    private WeakReference<Fragment> weakReference;

    public FetchFilters() {
        professors = new ArrayList<>();
        rooms = new ArrayList<>();
        semesters = new ArrayList<>();
        types = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ValueEventListener vListener = new ValueEventListener() {

            /** gets invoked when attached or when data changes within Firebase
             * @param snapshot current state of Firebase
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setFilters(snapshot);
                FilterFragment temp = (FilterFragment) weakReference.get();
                temp.setSpinnerCourse(titles);
                temp.setSpinnerCourseForm(types);
                temp.setSpinnerDozent(professors);
                temp.setSpinnerRoom(rooms);
                temp.setSpinnerSemester();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        firebase.getCourseDatabase("filters").addValueEventListener(vListener);
        return null;
    }

    /** loads data from "filters" entry in Firebase to later set the spinner
     * @param snapshot current state of Firebase
     */
    private void setFilters(DataSnapshot snapshot) {
        professors = (ArrayList<String>) snapshot.child("professors").getValue();
        semesters = (ArrayList<String>) snapshot.child("semesters").getValue();
        rooms = (ArrayList<String>) snapshot.child("rooms").getValue();
        titles = (ArrayList<String>) snapshot.child("titles").getValue();
        types = (ArrayList<String>) snapshot.child("types").getValue();

        professors.add(0, "");
        rooms.add(0, "");
        types.add(0, "");
        titles.add(0, "");
        semesters.add(0, "");
    }

    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }
}
