package com.jonathan.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    static Ringtone ringtone;
    @Override
    public void onReceive(Context context, Intent intent) {
        int request = intent.getIntExtra("Request", 0);
        Log.d("Alarm Receiver ", "Time to wake up");
        if(request == 0) {
            MainActivity.getWarningText().setText("Time to wake up");
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Alarm Clock")
                    .setContentText("Time to wake up")
                    .setAutoCancel(true)
                    .build();
            nm.notify(1, notification);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(context, uri);
            ringtone.play();
        }
        if(request == 1) {
            MainActivity.getWarningText().setText("");
            Toast.makeText(context, "Alarm stopped", Toast.LENGTH_SHORT).show();
            if(ringtone != null) {
                ringtone.stop();
            }
        }
    }
}
