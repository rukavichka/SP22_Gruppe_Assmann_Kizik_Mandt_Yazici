package com.example.Model;

import org.junit.Assert;
import org.junit.Test;

public class DateTest {

    /**Related to:
     * Anforderungen: Der User kann den Zeitplan bzw. Belegung eines Raumes sehen
     *              Mit Eingabe einer definierten Zeit kann der User leere Räume suchen
     * Test for compare method. The method Return -1 if the given object is after the current object
     */
    @Test
    public void CompareDatesTest(){
        String[] dateArray1 =  {"1", "12", "2021"};
        Date date1 = new Date(dateArray1);

        String[] dateArray2 =  {"12", "3", "2022"};
        Date date2 = new Date(dateArray2);

        Assert.assertEquals(-1, date1.compare(date2));
    }

    /**Related to:
     * Anforderungen: Der User kann den Zeitplan bzw. Belegung eines Raumes sehen
     *              Mit Eingabe einer definierten Zeit kann der User leere Räume suchen
     */
    @Test
    public void GetDayOfWeekTest(){
        String[] dateArray1 =  {"1", "12", "2021"};
        Date date1 = new Date(dateArray1);

        Assert.assertEquals("Mi", date1.getDayOfWeek());
    }
}
