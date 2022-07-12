package com.example.Service;

import androidx.annotation.NonNull;

import com.example.Model.Lecture;
import com.example.Model.VerificationProcess;
import com.example.SoapAPI.Firebase;
import com.example.View.LectureDetailsPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CheckMemberTest {

    DatabaseReference shortReference = FirebaseDatabase.getInstance().getReference("course-short-info");
    DatabaseReference longReference = FirebaseDatabase.getInstance().getReference("veranstaltungen");
    private static final Firebase firebase = new Firebase();
    HashMap<String, HashMap<String, String>> result = new HashMap<>();
    boolean isCourseMember;
    boolean CourseConatinsAMember;

    /**
     * Anforderung: Der User kann einer Veranstaltung beitreten
     * Adds course name to an entry which maps user_id to joined courses. Reference in Firebase data bank is "user_courses"
     */
    @Test
    public void AddCourseToUserIdTest() {
        String course = "Übungen zu Datenintegration";
        VerificationProcess.getInstance().setUserId(9999);      // "Default" User
        CheckMember checkMember = new CheckMember();

        // 1. we remove a course by the user (if it's already contained there)
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkMember.executeDeleteUserCourses(course);
            }
        });
        t1.start();
        try {
            t1.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. we assign a user to the Course
        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkMember.executeAddUserCourses(course);
            }
        });
        t2.start();

        // 2. we check in the firebase if the course is contained in the courses list for the userId
        Thread t3 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkIfUserIsAssignedToTheCourse(checkMember.getUserId(), course, checkMember);
            }
        });
        t3.start();

        try {
            t2.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t2.join();
            t3.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(isCourseMember);
    }


    /**
     * Anforderung: Der User kann einer Veranstaltung austreten
     * Removes course name from an entry which maps user_id to joined courses. Reference in Firebase data bank is "user_courses"
     */
    @Test
    public void DeleteCourseByUserIdTest() {
        String course = "Übungen zu Datenintegration";
        VerificationProcess.getInstance().setUserId(9999);      // "Default" User
        CheckMember checkMember = new CheckMember();

        // 1. we check in the firebase if the course is contained in the courses list for the userId
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkIfUserIsAssignedToTheCourse(checkMember.getUserId(), course, checkMember);
            }
        });
        t1.start();
        try {
            t1.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // if it's not, we aasgn a user to the course
        if (! isCourseMember) {
            // 1. we assign a user to the Course
            Thread t2 = new Thread(new Runnable(){
                @Override
                public void run() {
                    checkMember.executeAddUserCourses(course);
                }
            });
            t2.start();
            try {
                t2.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 2. we remove a user from the Course
        Thread t3 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkMember.executeDeleteUserCourses(course);
            }
        });
        t3.start();
        try {
            t3.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(VerificationProcess.getInstance().getUserId());

        // 3. we check in the firebase if the course is contained in the courses list for the userId
        Thread t4 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkIfUserIsAssignedToTheCourse(checkMember.getUserId(), course, checkMember);
            }
        });
        t4.start();
        try {
            t4.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertFalse(isCourseMember);
    }

    /**
     * Helper method for the Test functions AddCourseToUserIdTest(), DeleteCourseByUserIdTest()
     * checks if the course is contained in the course list of user
     * @param userId
     * @param courseName
     * @param checkMember
     */
    private void checkIfUserIsAssignedToTheCourse(String userId, String courseName, CheckMember checkMember) {
        List<String> tempList = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn: snapshot.getChildren()){
                    tempList.add(sn.getValue().toString());
                }
                isCourseMember = checkMember.checkList(tempList, courseName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        firebase.getCourseDatabase("user_courses/" + userId).addListenerForSingleValueEvent(valueEventListener);
    }

    @Test
    /**
     * Anforderung: Der User kann einer Veranstaltung beitreten
     * Adds current user_id to list of user_id's for the respective course in the reference "membership" in the Firebase data bank
     */
    public void ExecuteAddMembershipTest(){
        String course = "Übungen zu Datenintegration";
        VerificationProcess.getInstance().setUserId(9999);      // "Default" User
        CheckMember checkMember = new CheckMember();

        // 1. we remove a course by the user (if it's already contained there)
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkMember.executeDeleteMembership(course);
            }
        });
        t1.start();
        try {
            t1.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. we add a user from the Course
        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkMember.executeAddMembership(course);
            }
        });
        t2.start();
        try {
            t2.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. we check if a user is assined to the course
        Thread t3 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkIfTheCourseIsContainingTheMember(checkMember.getUserId(), course, checkMember);
            }
        });
        t3.start();
        try {
            t3.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(CourseConatinsAMember);
    }

    @Test
    /** Anforderung: Der User kann einer Veranstaltung austreten
     * This method deletes the user-id from the course entry in the reference "membership" in Firebase data bank
     */
    public void ExecuteDeleteMembershipTest(){
        String course = "Übungen zu Datenintegration";
        VerificationProcess.getInstance().setUserId(9999);      // "Default" User
        CheckMember checkMember = new CheckMember();

        // 1. we check if a user is assined to the course
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkIfTheCourseIsContainingTheMember(checkMember.getUserId(), course, checkMember);
            }
        });
        t1.start();
        try {
            t1.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // if not, we add the membership
        if (!CourseConatinsAMember) {
            Thread t = new Thread(new Runnable(){
                @Override
                public void run() {
                    checkMember.executeAddMembership(course);
                }
            });
            t.start();
            try {
                t.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 1. we remove a user from the Course
        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkMember.executeDeleteMembership(course);
            }
        });
        t2.start();
        try {
            t2.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. we remove a user from the Course
        Thread t3 = new Thread(new Runnable(){
            @Override
            public void run() {
                checkIfTheCourseIsContainingTheMember(checkMember.getUserId(), course, checkMember);
            }
        });
        t3.start();
        try {
            t3.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertFalse(CourseConatinsAMember);
    }

    /**
     * Helper method for the Test functions ExecuteAddMembershipTest(), ExecuteDeleteMembershipTest()
     * checks if the course is contained in the course list of user
     * @param userId
     * @param courseName
     * @param checkMember
     */
    private void checkIfTheCourseIsContainingTheMember(String userId, String courseName, CheckMember checkMember) {
        List<String> tempList = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn: snapshot.getChildren()){
                    tempList.add(sn.getValue().toString());
                }
                CourseConatinsAMember = checkMember.checkList(tempList, userId);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        firebase.getCourseDatabase("membership/" + courseName).addListenerForSingleValueEvent(valueEventListener);
    }

}
