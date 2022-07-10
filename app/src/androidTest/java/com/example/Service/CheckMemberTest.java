package com.example.Service;

import androidx.annotation.NonNull;

import com.example.Model.Lecture;
import com.example.Model.VerificationProcess;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class CheckMemberTest {

    DatabaseReference shortReference = FirebaseDatabase.getInstance().getReference("course-short-info");
    DatabaseReference longReference = FirebaseDatabase.getInstance().getReference("veranstaltungen");


    HashMap<String, HashMap<String, String>> result = new HashMap<>();


    /** Adds course name to an entry which maps user_id to joined courses. Reference in Firebase data bank is "user_courses"
     */
    @Test
    public void AddUserCoursesTest() {
        //CountDownLatch done = new CountDownLatch(1);
        VerificationProcess.getInstance().setUserId(9999);
        CheckMember checkMember = new CheckMember();
        System.out.println(checkMember.getUserId());
        //checkMember.executeAddUserCourses("Adaptive Numerische Verfahren für Operatorgleichungen");
        //checkMember.executeDeleteUserCourses("Datenbanksysteme");
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                checkMember.executeAddUserCourses("Adaptive Numerische Verfahren für Operatorgleichungen");
                //checkMember.executeDeleteUserCourses("Adaptive Numerische Verfahren für Operatorgleichungen");
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // checkMember.executeAddMembership("Datenbanksysteme");
        /**
        try {
            //checkMember.executeAddUserCourses("Adaptive Numerische Verfahren für Operatorgleichungen");

            //it will wait till the response is received from firebase.
            done.await();
            checkMember.executeAddUserCourses("Adaptive Numerische Verfahren für Operatorgleichungen");
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
         */
        System.out.println(VerificationProcess.getInstance().getUserId());


        /**
        Assert.assertTrue(result.keySet().contains("Algorithmen und Datenstrukturen"));
        Assert.assertTrue(result.keySet().contains("Adaptive Numerische Verfahren für Operatorgleichungen"));
        Assert.assertTrue(result.keySet().contains("Grundlagen der Analysis"));
        Assert.assertTrue(result.keySet().contains("Harmonische Analysis"));
        Assert.assertTrue(result.keySet().contains("Lernzentrum II (Übung/Angeleitetes Lernen)"));

        Assert.assertEquals(128, result.keySet().size());
         */

    }

}
