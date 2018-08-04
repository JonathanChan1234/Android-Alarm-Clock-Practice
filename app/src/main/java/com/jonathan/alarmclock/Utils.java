package com.jonathan.alarmclock;

import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

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

    public static Bundle findTimeDifference(int timeHour, int timeMinute) {
        Calendar currentTimeCalendar = Calendar.getInstance();
        Calendar alarmTimeCalendar = Calendar.getInstance();
        alarmTimeCalendar.set(Calendar.HOUR_OF_DAY, timeHour);
        alarmTimeCalendar.set(Calendar.MINUTE, timeMinute);
        int isGreaterThan = alarmTimeCalendar.compareTo(currentTimeCalendar);
        if(isGreaterThan < 0) {
            alarmTimeCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        long timeDifference = alarmTimeCalendar.getTimeInMillis() - currentTimeCalendar.getTimeInMillis();
        int minute = (int) timeDifference/(1000*60);
        int hour  = minute/60;
        if(hour > 0) {
            minute = minute%(hour*60);
        }
        Bundle bundle = new Bundle();
        bundle.putInt(HOUR, hour);
        bundle.putInt(MINUTE, minute);
        return bundle;
    }

    public static int checkTimeDifference(int timeHour, int timeMinute) {
        Calendar currentTimeCalendar = Calendar.getInstance();
        Calendar alarmTimeCalendar = Calendar.getInstance();
        alarmTimeCalendar.set(Calendar.HOUR_OF_DAY, timeHour);
        alarmTimeCalendar.set(Calendar.MINUTE, timeMinute);
        return alarmTimeCalendar.compareTo(currentTimeCalendar);
    }
}
