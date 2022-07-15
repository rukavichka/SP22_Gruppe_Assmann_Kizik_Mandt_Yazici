package com.example.Service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Model.Participant;
import com.example.View.ParticipantPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FetchParticipants extends AsyncTask<Integer, Void, Void> {
    private static final HashMap<String,Participant> result = new HashMap<>();
    private List<Participant> info;
    private WeakReference<Fragment> weakReference;
    private String courseName;
    private List<Participant> tempList = new ArrayList<>();

    public FetchParticipants(String courseName) {
        this.courseName = courseName;
    }

    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }

    @Override
    protected Void doInBackground(Integer... integers) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListenerUserID());


        return null;
    }

    private void setParticipantUserIDData(DataSnapshot snapshot) {
        for(DataSnapshot ds:snapshot.child("membership/" + courseName).getChildren()){
            String item = ds.getValue(String.class); //every user_id
            //result.put(item, new Participant(item));
            tempList.add(new Participant(item));        }
    }

    private void setParticipantName(DataSnapshot snapshot){
        for(Participant p : tempList){
            String name = snapshot.child("users/" + p.getUser_id() + "/" + "name").getValue(String.class);
            String iliasUsername = snapshot.child("users/" + p.getUser_id() + "/" + "iliasUsername").getValue(String.class);
            result.put(p.getUser_id(), new Participant(name,iliasUsername, p.getUser_id()));
        }
    }

    public class ValueEventListenerUserID implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            result.clear();
            setParticipantUserIDData(snapshot);
            setParticipantName(snapshot);
            ParticipantPageFragment temp = (ParticipantPageFragment) weakReference.get();
            temp.hideProgressBar();
            temp.recyclerViewParticipants(result);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
}
