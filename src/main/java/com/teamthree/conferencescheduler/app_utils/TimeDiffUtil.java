package com.teamthree.conferencescheduler.app_utils;

import java.util.Comparator;

public class TimeDiffUtil {

    private int minutes;
    private int hours;
    private int seconds;

    public TimeDiffUtil(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public static TimeDiffUtil difference(TimeDiffUtil start, TimeDiffUtil stop) {
        TimeDiffUtil diff = new TimeDiffUtil(0, 0, 0);

        if (stop.seconds > start.seconds) {
            --start.minutes;
            start.seconds += 60;
        }

        diff.seconds = start.seconds - stop.seconds;
        if (stop.minutes > start.minutes) {
            --start.hours;
            start.minutes += 60;
        }

        diff.minutes = start.minutes - stop.minutes;
        diff.hours = start.hours - stop.hours;

        return (diff);
    }

    public static Comparator<Integer> comparatorByTimeSpan(int time1, int time2) {
        return null;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getSeconds() {
        return seconds;
    }
}
