package com.example.Model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomTest {

    /** Related to:
     * Anforderungen: Der User kann den Zeitplan bzw. Belegung eines Raumes sehen
     *              Mit Eingabe einer definierten Zeit kann der User leere Räume suchen
     * Test for getLecture() method in the Room class.
     */
    @Test
    public void GetLectureTest(){
        ArrayList<String> times = new ArrayList<>(Arrays.asList("Di 14:00-16:00", "Do 16:00-18:00"));
        Lecture lecture = new Lecture("Adaptive Numerische Verfahren für Operatorgleichungen", "12.04.2022-12.07.2022", "3060/+3/A14", times);

        List<Lecture> lectureList = new ArrayList<>(Arrays.asList(lecture));

        Room room = new Room("3060/+3/A14");
        room.addLecture(lecture);

        String[] dateArray2 =  {"12", "07", "2022"};
        Date date2 = new Date(dateArray2);
        date2.setTime(14, 30);

        room.getLecture(date2);

        Assert.assertEquals("Adaptive Numerische Verfahren für Operatorgleichungen", room.getLecture(date2).getLectureName());

    }

    /** Related to:
     * Anforderungen: Der User kann den Zeitplan bzw. Belegung eines Raumes sehen
     *              Mit Eingabe einer definierten Zeit kann der User leere Räume suchen
     * Test for isFree() method in the Room class.
     */
    @Test
    public void IsFreeTest(){
        ArrayList<String> times = new ArrayList<>(Arrays.asList("Di 14:00-16:00", "Do 16:00-18:00"));
        Lecture lecture = new Lecture("Adaptive Numerische Verfahren für Operatorgleichungen", "12.04.2022-12.07.2022", "3060/+3/A14", times);

        List<Lecture> lectureList = new ArrayList<>(Arrays.asList(lecture));

        Room room = new Room("3060/+3/A14");
        room.addLecture(lecture);

        String[] dateArray1 =  {"1", "03", "2022"};
        Date date1 = new Date(dateArray1);

        String[] dateArray2 =  {"12", "07", "2022"};
        Date date2 = new Date(dateArray2);
        date2.setTime(14, 30);

        Assert.assertTrue(room.isFree(date1));
        Assert.assertFalse(room.isFree(date2));
    }


}
