package com.teamthree.conferencescheduler.app_utils;

import com.teamthree.conferencescheduler.entities.Session;

import java.util.*;
import java.util.stream.Collectors;

public class ProgramMaximumUtil {

    public static List<Session> execute(List<Session> dbSessions, String timeNow) {
        Map<Integer, Session> map = new HashMap<>();

        for (Session number : dbSessions) {
            String[] start = number.getStartHour().split(":");

            int hour = 0;
            int minute = 0;
            if (start[0].substring(0, 0).equals("0")) {
                hour = Integer.parseInt(start[0].substring(1));

            } else {
                hour = Integer.parseInt(start[0]);
            }

            minute = validate(start[1]);

            map.put((hour * 60) + minute, number);
        }
        map = map.entrySet().stream().sorted((a, b) -> {

            String[] start = a.getValue().getStartHour().split(":");

            int hour = 0;
            int minute = 0;
            int res = 0;
            if (start[0].substring(0, 0).equals("0")) {
                hour = Integer.parseInt(start[0].substring(1));

            } else {
                hour = Integer.parseInt(start[0]);
            }

            minute = validate(start[1]);
            res = (hour * 60) + minute;


            String[] start1 = b.getValue().getStartHour().split(":");

            int hour1 = 0;
            int minute1 = 0;
            int res1 = 0;
            if (start1[0].substring(0, 0).equals("0")) {
                hour1 = Integer.parseInt(start1[0].substring(1));

            } else {
                hour1 = Integer.parseInt(start1[0]);
            }

            minute1 = validate(start1[1]);
            res1 = (hour1 * 60) + minute1;

            if (a.getKey() != b.getKey()) {

                return a.getKey() - b.getKey();
            }
            return res - res1;

        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> e2, LinkedHashMap::new));


        String[] start = timeNow.split(":");

        int hour = 0;
        int minute = 0;
        int res = 0;
        if (start[0].substring(0, 0).equals("0")) {
            hour = Integer.parseInt(start[0].substring(1));
        } else {
            hour = Integer.parseInt(start[0]);
        }

        minute = validate(start[1]);
        res = (hour * 60) + minute;

        int minutes = res;

        int count = 0;
        boolean first = false;
        List<Session> result = new LinkedList<>();
        for (Map.Entry<Integer, Session> value : map.entrySet()) {

            if (!first) {
                String[] start1 = value.getValue().getStartHour().split(":");

                int hour1 = 0;
                int minute1 = 0;
                int res1 = 0;
                if (start1[0].substring(0, 0).equals("0")) {
                    hour1 = Integer.parseInt(start1[0].substring(1));

                } else {
                    hour1 = Integer.parseInt(start1[0]);
                }

                minute1 = validate(start1[1]);
                res1 = (hour1 * 60) + minute1;


                String[] start2 = value.getValue().getEndHour().split(":");

                int hour2 = 0;
                int minute2 = 0;
                int res2 = 0;
                if (start2[0].substring(0, 0).equals("0")) {
                    hour2 = Integer.parseInt(start2[0].substring(1));
                } else {
                    hour2 = Integer.parseInt(start2[0]);
                }

                minute2 = validate(start2[1]);
                res2 = (hour2 * 60) + minute2;

                if (minutes <= res1) {
                    first = true;
                    minutes = res2;

                    result.add(value.getValue());
                }

            } else {

                String[] start1 = value.getValue().getStartHour().split(":");

                int hour1 = 0;
                int minute1 = 0;
                int res1 = 0;
                if (start1[0].substring(0, 0).equals("0")) {
                    hour1 = Integer.parseInt(start1[0].substring(1));

                } else {
                    hour1 = Integer.parseInt(start1[0]);
                }

                minute1 = validate(start1[1]);
                res1 = (hour1 * 60) + minute1;

                String[] start2 = value.getValue().getEndHour().split(":");

                int hour2 = 0;
                int minute2 = 0;
                int res2 = 0;
                if (start2[0].substring(0, 0).equals("0")) {
                    hour2 = Integer.parseInt(start2[0].substring(1));

                } else {
                    hour2 = Integer.parseInt(start2[0]);
                }

                minute2 = validate(start2[1]);
                res2 = (hour2 * 60) + minute2;


                if (minutes <= res1) {

                    minutes = res2;

                    result.add(value.getValue());
                }

            }


        }
        return result;
    }

    private static int validate(String s) {
        int minute;
        if (s.length() == 1) {
            minute = Integer.parseInt(s + "0");
        } else if (s.substring(0, 0).equals("0")) {
            minute = Integer.parseInt(s.substring(1));
        } else {
            minute = Integer.parseInt(s);
        }
        return minute;
    }
}
