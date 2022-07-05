package com.example.Service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Model.Room;
import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.FirebaseItem;
import com.example.View.FilterFragment;
import com.example.View.RoomSearchPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FetchDozents  extends AsyncTask<Integer, Void, Void> {
    private static final Firebase firebase = new Firebase();
    private static final ArrayList<String> result = new ArrayList<>(Arrays.asList(""));
    private WeakReference<Fragment> weakReference;

    public FetchDozents() {
        super();
    }

    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setDozentsList(snapshot);
                FilterFragment temp = (FilterFragment)weakReference.get();
                temp.setSpinnerDozent(result);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        firebase.getCourseDatabase("veranstaltungen").addValueEventListener(valueEventListener);
        return null;
    }

    private void setDozentsList(DataSnapshot snapshot) {
        for(DataSnapshot ds:snapshot.getChildren()){
            String dozent = ds.child("respLecturer").getValue(String.class);
            if (!result.contains(dozent)) {
                result.add(dozent);
            }
        }
    }
}