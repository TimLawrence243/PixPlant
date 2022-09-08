package com.example.pixplanttrackerv1.Converter;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the application's use of converting Long dates and calculating if the plant needs to be
 * watered today, tomorrow, and/or yesterday
 */
class DateConverterTest {
    //Variables to test
    //86400000 is equal to one day in Long format
    Long today = System.currentTimeMillis() / 86400000;
    Long sevenDaysAgo = System.currentTimeMillis() / 86400000 - (86400000 * 7);
    Long sixDaysAgo = System.currentTimeMillis() / 86400000 - (86400000 * 6);
    Long eightDaysAgo = System.currentTimeMillis() / 86400000 - (86400000 * 8);

    /**
     * Testing 'needsWaterToday' calculations
     */
    @Test
    void needsWaterTodayTrue(){
        int daysBetween = 7;
        assertTrue(DateConverter.needsWaterToday(today, sevenDaysAgo, daysBetween));
    }

    /**
     * Testing 'needsWaterTomorrow' calculations
     */
    @Test
    void needsWaterTomorrowTrue(){
        int daysBetween = 7;
        assertTrue(DateConverter.needsWaterTomorrow(today, sixDaysAgo, daysBetween));
    }

    /**
     * Testing 'needsWaterYesterday' calculations
     */
    @Test
    void needsWaterYesterdayTrue(){
        int daysBetween = 7;
        assertTrue(DateConverter.needsWaterYesterday(today, eightDaysAgo, daysBetween));
    }
}