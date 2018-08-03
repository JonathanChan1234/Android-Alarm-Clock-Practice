package com.jonathan.alarmclock;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

public class MyDialogFragment extends DialogFragment {
    private int timeHour;
    private int timeMinute;
    private Handler handler;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        timeHour = bundle.getInt(Utils.HOUR);
        timeMinute = bundle.getInt(Utils.MINUTE);
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeHour = i;
                timeMinute = i1;
//                Bundle b = new Bundle();
//                b.putInt(Utils.HOUR, timeHour);
//                b.putInt(Utils.MINUTE, timeMinute);
//                Message msg = new Message();
//                msg.setData(b);
//                handler.sendMessage(msg);
            }
        };
        return new TimePickerDialog(getActivity(), listener, timeHour, timeMinute, false);
    }
}
