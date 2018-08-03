package com.jonathan.alarmclock;

public class Utils {
    public static final String HOUR = "HOUR";
    public static final String MINUTE = "MINUTE";
    public static final String TIME_PICKER = "TIME_PICKER";

    public static String minuteCorrection(int minute) {
        if(minute < 10) {
            return "0" + minute;
        }
        return minute + "";
    }
}
