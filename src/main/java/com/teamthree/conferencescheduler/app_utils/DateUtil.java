package com.teamthree.conferencescheduler.app_utils;

import com.teamthree.conferencescheduler.exceptions.ApplicationRuntimeException;

import java.util.Arrays;

public class DateUtil {

    public DateUtil() {
        super();
    }

    public static boolean checkIfPeriodIsValid(String startDate, String endDate) {
        int[] startPeriod = Arrays.stream(startDate.split("/")).mapToInt(Integer::parseInt).toArray();
        int[] endPeriod = Arrays.stream(endDate.split("/")).mapToInt(Integer::parseInt).toArray();

        if (startPeriod[0] > endPeriod[0] || startPeriod[1] > endPeriod[1] || startPeriod[2] > endPeriod[2]) {
            throw new ApplicationRuntimeException("Start date must be before End date!");
        }

        return true;
    }
}
