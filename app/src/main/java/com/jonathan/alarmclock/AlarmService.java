package com.jonathan.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

public class AlarmService extends Service {
    public static String DEBUG_TAG = "Alarm service";
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    public AlarmService() {
    }

    @Override
    public void onCreate() {
        Log.d(DEBUG_TAG, "Alarm service is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(DEBUG_TAG, "Alarm service started");

        //get hour and minute info from intent
        int timeHour = intent.getIntExtra(Utils.HOUR, 0);
        int timeMinute = intent.getIntExtra(Utils.MINUTE, 0);

        //setting calendar info
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        if(Utils.checkTimeDifference(timeHour, timeMinute) < 0) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        //setting up the alarm service
        Intent broadcastIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        broadcastIntent.putExtra("Request", 0);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, broadcastIntent, 0);
        alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(DEBUG_TAG, "Alarm service destroyed");
        alarmManager.cancel(pendingIntent);
        Intent broadcastIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        broadcastIntent.putExtra("Request", 1);
        sendBroadcast(broadcastIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//
//    public class MyBinder extends Binder {
//        public MyBinder() {
//        }
//        protected
//    }
}
