package com.teamthree.conferencescheduler.app_utils;

import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class DateUtil {

    public DateUtil() {
        super();
    }

    public static boolean checkIfPeriodIsValid(String startDate, String endDate) {
        int[] startPeriod = Arrays.stream(startDate.split("-")).mapToInt(Integer::parseInt).toArray();
        int[] endPeriod = Arrays.stream(endDate.split("-")).mapToInt(Integer::parseInt).toArray();

        // CHECK IF THE DATE IS BEFORE THE CURRENT DATE
        int[] todaySplit = Arrays.stream(getCurrentDateAsString().split("-"))
                .mapToInt(Integer::parseInt)
                .toArray();

        if (todaySplit[0] > startPeriod[0] || todaySplit[1] > startPeriod[1] || todaySplit[2] > startPeriod[2]) {
            throw new ApplicationRuntimeException("You cannot choose start date earlier than today!");
        }

        // CHECK IF THE END DATE IS BEFORE THE START DATE
        if (startPeriod[0] > endPeriod[0] || startPeriod[1] > endPeriod[1] || startPeriod[2] > endPeriod[2]) {
            throw new ApplicationRuntimeException("Start date must be before End date!");
        }

        return true;
    }

    public static String getTimeLapseOfSession(String startHour, String endHour) {
        //da predpolojim che podava startHour vuv format hh:MM
        String[] startHourTokens = startHour.split(":");
        String[] endHourTokens = endHour.split(":");

        TimeDiffUtil start = new TimeDiffUtil(Integer.parseInt(startHourTokens[0]), Integer.parseInt(startHourTokens[1]), 00);
        TimeDiffUtil end = new TimeDiffUtil(Integer.parseInt(endHourTokens[0]), Integer.parseInt(endHourTokens[1]), 00);

        TimeDiffUtil diff = TimeDiffUtil.difference(start, end);

        return diff.getHours() + ":" + diff.getMinutes();

    }

    public static int comparatorByStringDates(String dateOne, String dateTwo) {
        Comparator<String> comparator = (x, y) -> {
            int[] xColect = Arrays.stream(x.split("-"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int[] yCollect = Arrays.stream(y.split("-"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int year1 = xColect[0];
            int month1 = xColect[1];
            int day1 = xColect[2];

            int year2 = yCollect[0];
            int month2 = yCollect[1];
            int day2 = yCollect[2];


            if (year1 - year2 != 0) {
                return year1 - year2;
            }

            if (month1 - month2 != 0) {
                return month1 - month2;
            }

            if (day1 - day2 != 0) {
                return day1 - day2;
            }

            return 0;
        };

        return comparator.compare(dateOne, dateTwo);
    }

    public static String getCurrentDateAsString() {
        return LocalDate.now().toString();
    }

    public static String getCurrentTimeAsString() {
        String[] runtime = LocalDateTime.now().toString().split(":");
        return runtime[0].substring(runtime[0].length() - 2) + ":" + runtime[1];
    }
}
