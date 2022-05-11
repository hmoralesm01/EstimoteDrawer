package com.example.estimotedrawer.addBooking;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DialogFecha extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    onFechaSeleccionada f;


    @Override
    public void onAttach(Context context) {
        f=(onFechaSeleccionada)context;
        super.onAttach(context);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c= Calendar.getInstance();
        int año=c.get(Calendar.YEAR);
        int mes=c.get(Calendar.MONTH);
        int dia=c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,año,mes,dia);
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        GregorianCalendar g=new GregorianCalendar(i,i2,i3);
        f.onResultadoFecha(g);
    }
    public interface onFechaSeleccionada{
        public void onResultadoFecha(GregorianCalendar fecha);
    }



}
