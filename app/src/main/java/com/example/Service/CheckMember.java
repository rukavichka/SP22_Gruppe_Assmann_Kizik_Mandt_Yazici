package com.example.Service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Model.CheckMemberItem;
import com.example.Model.VerificationProcess;
import com.example.SoapAPI.Firebase;
import com.example.View.LectureDetailsPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CheckMember {

    private final String user_ID = Integer.toString(VerificationProcess.getInstance().getUserId());
    private static final Firebase firebase = new Firebase();
    String courseName;
    boolean inCourseList;
    private WeakReference<Fragment> weakReference;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user_courses/" + VerificationProcess.getInstance().userId);



    private boolean checkList(List<String> list, String courseName) {
        if(list.isEmpty()){
            return false;
        }
        else if (list.contains(courseName)){
            return true;
        }
        else{
            return false;
        }
    }


    public void executeCheckMembership() {
        List<String> tempList = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn: snapshot.getChildren()){
                    tempList.add(sn.getValue().toString());
                }
                LectureDetailsPageFragment temp = (LectureDetailsPageFragment)weakReference.get();
                temp.setIsCourseMember(checkList(tempList,courseName));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        firebase.getCourseDatabase("user_courses/" + user_ID).addListenerForSingleValueEvent(valueEventListener);
    }

    public void executeDeleteMembership(String courseName){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp = "test";
                for(DataSnapshot sn : snapshot.getChildren()){
                    if(sn.getValue().toString().equals(courseName)){
                        temp = sn.getKey();
                    }
                }
                if(temp != "test"){
                    databaseReference.child(temp).removeValue();
                }
                executeCheckMembership();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void executeAddMembership(String courseName){
        databaseReference.push().setValue(courseName);
        executeCheckMembership();
    }

    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }


    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

}
