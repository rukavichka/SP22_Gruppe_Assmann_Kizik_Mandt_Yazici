package com.example.Service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Model.Participant;
import com.example.Model.Room;
import com.example.Model.VerificationProcess;
import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.FirebaseItem;
import com.example.View.ParticipantPageFragment;
import com.example.View.RoomSearchPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FetchParticipants extends AsyncTask<Integer, Void, Void> {
    private static final HashMap<String,Participant> result = new HashMap<>();
    private List<Participant> info;
    private WeakReference<Fragment> weakReference;
    private String courseName;

    public FetchParticipants(String courseName) {
        this.courseName = courseName;
    }

    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setParticipantSmallData(snapshot);
                ParticipantPageFragment temp = (ParticipantPageFragment) weakReference.get();
                temp.hideProgressBar();
                temp.recyclerViewParticipants(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("membership/" + courseName);
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
        return null;
    }

    private void setParticipantSmallData(DataSnapshot snapshot) {
        for(DataSnapshot ds:snapshot.getChildren()){
            String item = ds.getValue(String.class);
            result.put(item, new Participant(item));

        }
    }
}
