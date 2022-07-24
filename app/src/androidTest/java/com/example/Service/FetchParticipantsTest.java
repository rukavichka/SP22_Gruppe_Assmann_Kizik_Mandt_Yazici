package com.example.Service;

import android.provider.Telephony;

import androidx.annotation.NonNull;

import com.example.Model.Participant;
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
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FetchParticipantsTest {

    List<Participant> tempList = new ArrayList<>();
    HashMap<String,Participant> result = new HashMap<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FetchParticipants fetchParticipants = new FetchParticipants("Übungen zu Datenintegration");

    /**
     * Anforderung: Der User kann die Teilnehmer aus ILIAS einer Veranstaltung einsehen, die er selbst beigetreten ist
     * We test here two methods from FetchParticipants Class
     * (setParticipantUserIDData and setParticipantName)
     */
    @Test
    public void SetParticipantDataTest() {
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

        // 2. we add a user to the Course
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


        CountDownLatch done = new CountDownLatch(1);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fetchParticipants.setParticipantUserIDData(snapshot);
                tempList = fetchParticipants.getParticipantList();
                done.countDown();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

         }
        });
        try {
         //it will wait till the response is received from firebase.
            done.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        List<String> participantIds = new ArrayList<>();
        for (Participant p: tempList ) {
            participantIds.add(p.getUser_id());
        }

        // test for setParticipantUserIDData(), that id="9999" is contained for thecourse "Übungen zu Datenintegration"
        Assert.assertTrue(participantIds.contains("9999"));

        CountDownLatch done1 = new CountDownLatch(1);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fetchParticipants.setParticipantName(snapshot);
                result = fetchParticipants.getResult();
                done1.countDown();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        try {
            //it will wait till the response is received from firebase.
            done1.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(result.keySet().contains("9999"));
        Assert.assertEquals("Default Member", result.get("9999").getName());
        Assert.assertEquals("Default", result.get("9999").getIliasUsername());
    }

}
