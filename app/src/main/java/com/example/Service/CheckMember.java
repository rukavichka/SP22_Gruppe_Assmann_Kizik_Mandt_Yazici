package com.example.Service;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Model.VerificationProcess;
import com.example.SoapAPI.Firebase;
import com.example.View.LectureDetailsPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private DatabaseReference databaseReferenceUserCourses = FirebaseDatabase.getInstance().getReference("user_courses/" + VerificationProcess.getInstance().userId);


    /**
     * @param list of joined courses
     * @param courseName current course
     * @return true when the list contains the current course
     */
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


    /**
     * checks if the current user already joined the selected course looking up if the "user_courses" entry in firebase data bank contains selected course
     */
    public void executeCheckUserCourses() {
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

    /** Deletes course name in entry which maps user_id to joined courses. Reference in Firebase data bank is "user_courses"
     * @param courseName acts as key
     */
    public void executeDeleteUserCourses(String courseName){
        databaseReferenceUserCourses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp = "test";
                for(DataSnapshot sn : snapshot.getChildren()){
                    if(sn.getValue().toString().equals(courseName)){
                        temp = sn.getKey();
                    }
                }
                if(temp != "test"){
                    databaseReferenceUserCourses.child(temp).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /** Adds course name to an entry which maps user_id to joined courses. Reference in Firebase data bank is "user_courses"
     * @param courseName acts as key
     */
    public void executeAddUserCourses(String courseName){
        databaseReferenceUserCourses.push().setValue(courseName);
    }

    /** Adds current user_id to list of user_id's for the respective course in the reference "membership" in the Firebase data bank
     * @param courseName the course name acts as key
     */
    public void executeAddMembership(String courseName){
        DatabaseReference databaseReferenceMembership = FirebaseDatabase.getInstance().getReference("membership/" + courseName);
        databaseReferenceMembership.push().setValue(user_ID);
    }

    /**
     * This method deletes the user-id from the course entry in the reference "membership" in Firebase data bank
     * @param courseName the course name acts as key
     */
    public void executeDeleteMembership(String courseName){
        DatabaseReference databaseReferenceMembership = FirebaseDatabase.getInstance().getReference("membership/" + courseName);
        databaseReferenceMembership.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp = "test";
                for(DataSnapshot sn : snapshot.getChildren()){
                    if(sn.getValue().toString().equals(user_ID)){
                        temp = sn.getKey();
                    }
                }
                if(temp != "test"){
                    databaseReferenceMembership.child(temp).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }


    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

}
