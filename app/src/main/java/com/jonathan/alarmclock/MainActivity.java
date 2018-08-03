package com.jonathan.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button startButton, stopButton;
    static TextView warningText, timeText;

    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        timeText = findViewById(R.id.timeText);
        warningText = findViewById(R.id.warningText);

        startButton.setOnClickListener(alarmStart);
        stopButton.setOnClickListener(alarmStop);

        timeText.setText(timeHour + ":" + Utils.minuteCorrection(timeMinute));

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    View.OnClickListener alarmStart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            warningText.setText("");
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    timeText.setText("Alarm: " + i + ":" + Utils.minuteCorrection(i1));
                    timeHour = i;
                    timeMinute = i1;
                    setAlarm();
                }
            }, hour, minute, false).show();
        }
    };

    View.OnClickListener alarmStop = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            warningText.setText("");
            cancelAlarm();
        }
    };

    protected void onResume() {
        super.onResume();
        timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    }

    private void setAlarm() {
        Log.d("Alarm", "Alarm started");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        Log.d("Trigger Time", calendar.getTimeInMillis() + "");
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("Request", 0);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        if(alarmManager != null) {
            alarmManager.cancel(pendingIntent);
//            AlarmReceiver.ringtone.stop();
            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
            intent.putExtra("Request", 1);
            sendBroadcast(intent);
        }
    }

    public static TextView getWarningText() {
        return warningText;
    }
}
