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
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class FetchCoursesTest {

    DatabaseReference shortReference = FirebaseDatabase.getInstance().getReference("course-short-info");
    DatabaseReference longReference = FirebaseDatabase.getInstance().getReference("veranstaltungen");

    FetchCourses fetchCourses = new FetchCourses();
    //HashMap<String, HashMap<String, String>> result = new HashMap<>();
    ArrayList<Lecture> result = new ArrayList<>();
    ArrayList<Lecture> detailedLectureList;
    List<String> joinedCourses = new ArrayList<>();


    /**
     * Anforderung: Dem User wird eine Liste der von allen Veranstaltungen des FB12 angezeigt
     * We check some courses, that must be contained on "Alle Veranstaltungen" page, if they are
     * really contained on it. We also check the number of courses on "Alle Verarnstaltungen" page
     */
    @Test
    public void FetchCoursesListTest() {
        fetchCourses.setIsJoined(false);
        CountDownLatch done = new CountDownLatch(1);
        shortReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fetchCourses.setCourseSmallData(snapshot);
                result = fetchCourses.getInfo();
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

        ArrayList<String> listOfAllLectures = new ArrayList<>();
        for (Lecture l:result){
            listOfAllLectures.add(l.getLectureName());
        }

        Assert.assertTrue(listOfAllLectures.contains("Algorithmen und Datenstrukturen"));
        Assert.assertTrue(listOfAllLectures.contains("Adaptive Numerische Verfahren für Operatorgleichungen"));
        Assert.assertTrue(listOfAllLectures.contains("Grundlagen der Analysis"));
        Assert.assertTrue(listOfAllLectures.contains("Harmonische Analysis"));
        Assert.assertTrue(listOfAllLectures.contains("Lernzentrum II (Übung/Angeleitetes Lernen)"));

        Assert.assertEquals(128, listOfAllLectures.size());

    }

    /**
     * Anforderung: Dem User wird eine Liste der von allen Veranstaltungen des FB12 angezeigt
     * We take some courses from the list of courses and check that the short information
     * (Semester, Dozent/Tutor, Nummer, Veranstaltungen) to each of this courses is correct
     */
    @Test
    public void FetchCoursesShortInfoTest() {
        fetchCourses.setIsJoined(false);
        CountDownLatch done = new CountDownLatch(1);
        shortReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fetchCourses.setCourseSmallData(snapshot);
                result = fetchCourses.getInfo();
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

        HashMap<String, Lecture> lectureMap = new HashMap<>();
        ArrayList<String> listOfAllLectures = new ArrayList<>();
        for (Lecture l:result){
            listOfAllLectures.add(l.getLectureName());
            if (l.getLectureName().equals("Algorithmen und Datenstrukturen")) {
                Lecture lectureAuD = new Lecture(l.getLectureName(), l.getProfessorName(), l.getSemester(),
                        l.getNumber(), l.getForm(), l.getLectureRoom());
                lectureMap.put("Algorithmen und Datenstrukturen", lectureAuD);
            }
            if (l.getLectureName().equals("Adaptive Numerische Verfahren für Operatorgleichungen")) {
                Lecture lectureAnF = new Lecture(l.getLectureName(), l.getProfessorName(), l.getSemester(),
                        l.getNumber(), l.getForm(), l.getLectureRoom());
                lectureMap.put("Adaptive Numerische Verfahren für Operatorgleichungen", lectureAnF);
            }
        }

        Assert.assertTrue(listOfAllLectures.contains("Algorithmen und Datenstrukturen"));

        Lecture lectureAuD = lectureMap.get("Algorithmen und Datenstrukturen");
        Assert.assertEquals("SoSe 2022", lectureAuD.getSemester());
        Assert.assertEquals("Vorlesung", lectureAuD.getForm());
        Assert.assertEquals("LV-12-079-017", lectureAuD.getNumber());
        Assert.assertEquals("Komusiewicz, C.; ", lectureAuD.getProfessorName());

        Assert.assertTrue(listOfAllLectures.contains("Adaptive Numerische Verfahren für Operatorgleichungen"));
        Lecture lectureAnF = lectureMap.get("Adaptive Numerische Verfahren für Operatorgleichungen");
        Assert.assertEquals("SoSe 2022", lectureAnF.getSemester());
        Assert.assertEquals("Vorlesung", lectureAnF.getForm());
        Assert.assertEquals("LV-12-105-012", lectureAnF.getNumber());
        Assert.assertEquals("Hansen, M.; ", lectureAnF.getProfessorName());

    }

    /**
     * Anforderung: Details der Veranstaltungen werden visualisiert (Anbieter, Semester, Zeit, Raum)
     * We check the details to one of the courses
     */
    @Test
    public void DetailsOfTheCourseTest() {
        fetchCourses.setIsJoined(false);
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

        CountDownLatch done = new CountDownLatch(1);
        shortReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fetchCourses.setCourseSmallData(snapshot);
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

        HashMap<String, String> filterparameters = new HashMap<>();
        filterparameters.put("titleSemabh", "");
        filterparameters.put("respLecturer", "");
        filterparameters.put("semester", "");
        filterparameters.put("room", "");

        fetchCourses.filterFunction(filterparameters);
        result = fetchCourses.getInfo();

        for (Lecture l:result) {
            System.out.println(l.getLectureName() + " " + l.getProfessorName()+ " " + l.getSemester() + " " + l.getLectureRoom());
        }
        Assert.assertTrue(result.isEmpty());

    }


    /**
     * Anforderung: Der User kann die Veranstaltungen mit den Filtereinstellungen aussortieren
     * We want to check the output filtered with "form"="Seminar/Mittelseminar"
     */
    @Test
    public void FilterCoursesByTitleTest() {

        CountDownLatch done = new CountDownLatch(1);
        shortReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fetchCourses.setCourseSmallData(snapshot);
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

        HashMap<String, String> filterparameters = new HashMap<>();
        filterparameters.put("titleSemabh", "Harmonische Analysis");
        filterparameters.put("respLecturer", "");
        filterparameters.put("semester", "");
        filterparameters.put("room", "");

        fetchCourses.filterFunction(filterparameters);
        result = fetchCourses.getInfo();

        for (Lecture l:result) {
            System.out.println(l.getLectureName() + " " + l.getProfessorName()+ " " + l.getSemester() + " " + l.getLectureRoom());
        }
        Assert.assertEquals("Harmonische Analysis", result.get(0).getLectureName());

    }

    /**
     * Anforderung: Dem User wird eine Liste von Veranstaltungen gezeigt, die er momentan belegt
     * We check some courses, that must be contained on "Alle Verarnstaltungen" page, if they are
     * really contained on it. We also check the number of courses on "Alle Verarnstaltungen" page
     */
    @Test
    public void FetchJoinedCoursesInfoTest() {

        fetchCourses.setIsJoined(true);

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
                fetchCourses.setCourseSmallData(snapshot);
                result = fetchCourses.getInfo();
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

        ArrayList<String> listOfAllLectures = new ArrayList<>();
        for (Lecture l:result){
            listOfAllLectures.add(l.getLectureName());
        }

        Assert.assertTrue(listOfAllLectures.contains("Übungen zu Datenintegration"));
    }

}
