package com.example.Model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LectureTest {

    /** Related to:
     * Anforderungen: Der User kann den Zeitplan bzw. Belegung eines Raumes sehen
     *              Mit Eingabe einer definierten Zeit kann der User leere Räume suchen
     * Test for isBusy() method in Lecture class.
     */
    @Test
    public void TestLectureIsBusy() {
        ArrayList<String> times = new ArrayList<>(Arrays.asList("Di 14:00-16:00", "Do 16:00-18:00"));
        Lecture lecture = new Lecture("Adaptive Numerische Verfahren für Operatorgleichungen", "12.04.2022-12.07.2022", "3060/+3/A14", times);

        String[] dateArray1 =  {"1", "03", "2022"};
        Date date1 = new Date(dateArray1);

        String[] dateArray2 =  {"12", "07", "2022"};
        Date date2 = new Date(dateArray2);
        date2.setTime(14, 30);

        Assert.assertFalse(lecture.isBusy(date1));
        Assert.assertTrue(lecture.isBusy(date2));
    }
}
