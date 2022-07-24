package com.example.Service;

import androidx.annotation.NonNull;

import com.example.Model.Lecture;
import com.example.Model.VerificationProcess;
import com.example.SoapAPI.Firebase;
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


public class FetchCoursesTest {

    DatabaseReference shortReference = FirebaseDatabase.getInstance().getReference("course-short-info");
    DatabaseReference longReference = FirebaseDatabase.getInstance().getReference("veranstaltungen");

    FetchCourses fetchCourses = new FetchCourses();
    HashMap<String, HashMap<String, String>> result = new HashMap<>();
    ArrayList<Lecture> detailedLectureList;
    List<String> joinedCourses = new ArrayList<>();


    /**
     * Anforderung: Dem User wird eine Liste der von allen Veranstaltungen des FB12 angezeigt
     * We check some courses, that must be contained on "Alle Verarnstaltungen" page, if they are
     * really contained on it. We also check the number of courses on "Alle Verarnstaltungen" page
     */
    @Test
    public void FetchCoursesListTest() {
        CountDownLatch done = new CountDownLatch(1);
        shortReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fetchCourses.setCourseSmallData(snapshot);
                result = fetchCourses.getResult();
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

        Assert.assertTrue(result.keySet().contains("Algorithmen und Datenstrukturen"));
        Assert.assertTrue(result.keySet().contains("Adaptive Numerische Verfahren für Operatorgleichungen"));
        Assert.assertTrue(result.keySet().contains("Grundlagen der Analysis"));
        Assert.assertTrue(result.keySet().contains("Harmonische Analysis"));
        Assert.assertTrue(result.keySet().contains("Lernzentrum II (Übung/Angeleitetes Lernen)"));

        Assert.assertEquals(128, result.keySet().size());

    }

    /**
     * Anforderung: Dem User wird eine Liste der von allen Veranstaltungen des FB12 angezeigt
     * We take some courses from the list of courses and check that the short information
     * (Semester, Dozent/Tutor, Nummer, Veranstaltungen) to each of this courses is correct
     */
    @Test
    public void FetchCoursesShortInfoTest() {

        CountDownLatch done = new CountDownLatch(1);
        shortReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fetchCourses.setCourseSmallData(snapshot);
                result = fetchCourses.getResult();
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

        Assert.assertTrue(result.keySet().contains("Algorithmen und Datenstrukturen"));
        HashMap<String, String> infoAD = result.get("Algorithmen und Datenstrukturen");
        Assert.assertEquals("SoSe 2022", infoAD.get("semester"));
        Assert.assertEquals("Vorlesung", infoAD.get("form"));
        Assert.assertEquals("LV-12-079-017", infoAD.get("number"));
        Assert.assertEquals("Komusiewicz, C.; ", infoAD.get("prof"));

        Assert.assertTrue(result.keySet().contains("Adaptive Numerische Verfahren für Operatorgleichungen"));
        HashMap<String, String> infoNV = result.get("Adaptive Numerische Verfahren für Operatorgleichungen");
        Assert.assertEquals("SoSe 2022", infoNV.get("semester"));
        Assert.assertEquals("Vorlesung", infoNV.get("form"));
        Assert.assertEquals("LV-12-105-012", infoNV.get("number"));
        Assert.assertEquals("Hansen, M.; ", infoNV.get("prof"));

    }

    /**
     * Anforderung: Details der Veranstaltungen werden visualisiert (Anbieter, Semester, Zeit, Raum)
     * We check the details to one of the courses
     */
    @Test
    public void DetailsOfTheCourseTest() {

        String courseName = "Algorithmen und Datenstrukturen";

        CountDownLatch done = new CountDownLatch(1);
        fetchCourses.setCourseName(courseName);
        FirebaseDatabase.getInstance().getReference("veranstaltungen")
                .orderByChild("titleSemabh").equalTo(courseName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fetchCourses.setCourseLargeData(snapshot);
                detailedLectureList = fetchCourses.getInfo();
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

        Assert.assertEquals("Komusiewicz, C.; ", detailedLectureList.get(0).getProfessorName());
        Assert.assertEquals("Vorlesung", detailedLectureList.get(0).getForm());
        // check number of periods (shown as dropdown) for the course
        Assert.assertEquals(4, detailedLectureList.size());
    }


    /**
     * Anforderung: Der User kann die Veranstaltungen mit den Filtereinstellungen aussortieren
     * If all the filters are empty (empty strings), we must get empty output
     */
    @Test
    public void FilterCoursesEmptyResultTest() {

        HashMap<String, String> filterparameters = new HashMap<>();
        filterparameters.put("titleSemabh", "");
        filterparameters.put("respLecturer", "");
        filterparameters.put("semester", "");
        filterparameters.put("room", "");
        filterparameters.put("form", "");

        CountDownLatch done = new CountDownLatch(1);
        longReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fetchCourses.setFilteredCourseSmallData(snapshot, filterparameters);
                result = fetchCourses.getResult();
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
        Assert.assertTrue(result.isEmpty());
    }


    /**
     * Anforderung: Der User kann die Veranstaltungen mit den Filtereinstellungen aussortieren
     * We want to check the output filtered with "form"="Seminar/Mittelseminar"
     */
    @Test
    public void FilterCoursesBySeminarFormTest() {

        HashMap<String, String> filterparameters = new HashMap<>();
        filterparameters.put("titleSemabh", "");
        filterparameters.put("respLecturer", "");
        filterparameters.put("semester", "");
        filterparameters.put("room", "");
        filterparameters.put("form", "Seminar/Mittelseminar");

        CountDownLatch done = new CountDownLatch(1);
        longReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fetchCourses.setFilteredCourseSmallData(snapshot, filterparameters);
                result = fetchCourses.getResult();
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

         Assert.assertTrue(result.keySet().contains("Biological Data Visualization/Darstellung biologischer Daten"));
         Assert.assertTrue(result.keySet().contains("Deep Learning Methods"));

         Assert.assertEquals(20, result.keySet().size());

    }

    /**
     * Anforderung: Dem User wird eine Liste von Veranstaltungen gezeigt, die er momentan belegt
     * We check some courses, that must be contained on "Alle Verarnstaltungen" page, if they are
     * really contained on it. We also check the number of courses on "Alle Verarnstaltungen" page
     */
    @Test
    public void FetchJoinedCoursesInfoTest() {

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

        try {
            t2.sleep(1000);        // is necessary to wait till the data will be pushed to Firebase in the separate thread
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int userId = 9999;
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("user_courses/" + userId);
        joinedCourses.clear();

        CountDownLatch done = new CountDownLatch(2);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren()) {
                    joinedCourses.add(sn.getValue().toString());
                }
                done.countDown();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fetchCourses.setJoinedCourses(joinedCourses);

        longReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fetchCourses.setJoinedCourseData(snapshot);
                result = fetchCourses.getResult();
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

        Assert.assertTrue(result.keySet().contains("Übungen zu Datenintegration"));
    }

}
