package com.example.estimotedrawer.addBooking;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DialogHora extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    onHoraSeleccionada f;
    @Override
    public void onAttach(Context context) {
        f=(onHoraSeleccionada)context;
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c=Calendar.getInstance();
        int hora=c.get(Calendar.HOUR);
        int minutos=c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),this,hora,minutos,true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i2) {

        f.onResultadoHora(i, i2);
    }

    public interface onHoraSeleccionada{
        public void onResultadoHora(int i,int i2);
    }

}
