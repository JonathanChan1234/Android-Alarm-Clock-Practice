package com.jonathan.alarmclock;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button startButton, stopButton;
    static TextView warningText, timeText;

    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);

    public static Intent serviceIntent;
    Boolean isActivated;

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
    }

    View.OnClickListener alarmStart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            warningText.setText("");
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            Log.d("IsActivated", isActivated + "");
            new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
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
        if(isActivated == null || !isActivated) {
            isActivated = true;
            timeText.setText("Alarm: " + timeHour + ":" + Utils.minuteCorrection(timeMinute));
            Bundle timeBundle = Utils.findTimeDifference(timeHour, timeMinute);
            int minuteDifference = timeBundle.getInt(Utils.MINUTE);
            int hourDifference = timeBundle.getInt(Utils.HOUR);
            Log.d("Time Toast", "Hour: " + hourDifference + " Minute: " + minuteDifference);
            Log.d("Alarm", "Alarm started");
            serviceIntent = new Intent(MainActivity.this, AlarmService.class);
            serviceIntent.putExtra(Utils.MINUTE, timeMinute);
            serviceIntent.putExtra(Utils.HOUR, timeHour);
            startService(serviceIntent);
        }
        else {
            Toast.makeText(MainActivity.this, "This is also an alarm activated", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelAlarm() {
        if(isActivated == null || !isActivated) {
            Toast.makeText(MainActivity.this, "There is currently no alarm clock running", Toast.LENGTH_SHORT).show();
        }
        else {
            isActivated = false;
            stopService(serviceIntent);
        }
    }

    public static TextView getWarningText() {
        return warningText;
    }
}
