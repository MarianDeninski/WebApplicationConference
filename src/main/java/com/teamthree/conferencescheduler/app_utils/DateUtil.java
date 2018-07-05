package com.teamthree.conferencescheduler.app_utils;

import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;

import java.util.Arrays;

public class DateUtil {

    public DateUtil() {
        super();
    }

    public static boolean checkIfPeriodIsValid(String startDate, String endDate) {
        int[] startPeriod = Arrays.stream(startDate.split("-")).mapToInt(Integer::parseInt).toArray();
        int[] endPeriod = Arrays.stream(endDate.split("-")).mapToInt(Integer::parseInt).toArray();

        if (startPeriod[0] > endPeriod[0] || startPeriod[1] > endPeriod[1] || startPeriod[2] > endPeriod[2]) {
            throw new ApplicationRuntimeException("Start date must be before End date!");
        }

        return true;
    }

    public static String getTimeLapseOfSession(String startHour,String endHour){
        //da predpolojim che podava startHour vuv format hh:MM
        String[] startHourTokens = startHour.split(":");
        String[] endHourTokens = endHour.split(":");

        TimeDiffUtil start = new TimeDiffUtil(Integer.parseInt(startHourTokens[0]),Integer.parseInt(startHourTokens[1]) , 00);
        TimeDiffUtil end = new TimeDiffUtil(Integer.parseInt(endHourTokens[0]), Integer.parseInt(endHourTokens[1]), 00);

        TimeDiffUtil diff = TimeDiffUtil.difference(start,end);

        return diff.getHours() +":"+diff.getMinutes();

    }
}
