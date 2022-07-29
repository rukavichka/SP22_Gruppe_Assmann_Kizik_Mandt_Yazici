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

/**
 * class to load participant data from database
 * @field weakReference: lightweight reference to view object which created this class
 * @field result:
 */
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

    /** doInBAckground is being invoked by the execute method which comes with AsyncTask
     * @param integers not used here
     */
    @Override
    protected Void doInBackground(Integer... integers) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListenerUserID());
        return null;
    }

    /** gets all the user_ID's of the correspondent course from the membership entry in firebase
     * and adds it to tempList
     * @param snapshot the current state of Firebase
     */
    public void setParticipantUserIDData(DataSnapshot snapshot) {
        for(DataSnapshot ds:snapshot.child("membership/" + courseName).getChildren()){
            String item = ds.getValue(String.class); //every user_id
            tempList.add(new Participant(item));        }
    }

    /** gets the participant's name and username and maps it in result by using user_ID
     * @param snapshot the current state of Firebase
     */
    public void setParticipantName(DataSnapshot snapshot){
        for(Participant p : tempList){
            String name = snapshot.child("users/" + p.getUser_id() + "/" + "name").getValue(String.class);
            String iliasUsername = snapshot.child("users/" + p.getUser_id() + "/" + "iliasUsername").getValue(String.class);
            result.put(p.getUser_id(), new Participant(name,iliasUsername, p.getUser_id()));
        }
    }

    public class ValueEventListenerUserID implements ValueEventListener{

        /** gets invoked when attached or when data changes within Firebase
         * @param snapshot current state of Firebase
         */
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
    public List<Participant> getParticipantList(){
        return this.tempList;
    }

    public HashMap<String,Participant> getResult(){
        return this.result;
    }
}
