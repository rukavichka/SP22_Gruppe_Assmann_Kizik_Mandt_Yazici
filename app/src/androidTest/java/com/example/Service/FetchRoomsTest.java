package com.example.Service;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import com.example.Model.Date;
import com.example.Model.Lecture;
import com.example.Model.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FetchRoomsTest {

    DatabaseReference roomsReference = FirebaseDatabase.getInstance().getReference("rooms");
    FetchRooms fetchRooms = new FetchRooms();
    ArrayList<Room> result = new ArrayList<>();

    /**
     * Anforderung: Der User kann den Zeitplan bzw. Belegung eines Raumes sehen
     *              Mit Eingabe einer definierten Zeit kann der User leere Räume suchen
     */
    @Test
    public void SetRoomsListTest() {
        CountDownLatch done = new CountDownLatch(1);
        roomsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fetchRooms.setRoomsList(snapshot);
                result = fetchRooms.getResult();
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

        List<String> roomsList = new ArrayList<String>();
        List<Lecture> lectureList = new ArrayList<>();

        LocalDate ldate = LocalDate.of(Integer.parseInt("2022"), Integer.parseInt("5"), Integer.parseInt("10"));
        Date date = new Date(ldate);
        date.setTime(14, 10);
        for (Room entry: result) {
            roomsList.add(entry.getRoomNumber());

            lectureList.add(entry.getLecture(date));
        }
        //System.out.println(result);
        Assert.assertTrue(roomsList.contains("3060/+3/A14"));
        Assert.assertEquals("Adaptive Numerische Verfahren für Operatorgleichungen", lectureList.get(0).getLectureName());

    }


}
