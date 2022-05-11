package com.example.estimotedrawer.addBooking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estimotedrawer.MainActivity;
import com.example.estimotedrawer.R;
import com.example.estimotedrawer.databinding.ActivityAddBookingBinding;
import com.example.estimotedrawer.models.Booking;
import com.example.estimotedrawer.models.Local;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddBookingActivity extends AppCompatActivity implements DialogFecha.onFechaSeleccionada, DialogHora.onHoraSeleccionada, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ActivityAddBookingBinding binding;
    private String fecha;
    private String hora;
    private String nombreLocal;
    private String phone;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fecha = "";
        hora ="";

        binding.bFecha.setOnClickListener(this);
        binding.bHora.setOnClickListener(this);
        binding.bCrear.setOnClickListener(this);
        binding.bCancelar.setOnClickListener(this);

        spinnerSet();


    }

    @Override
    public void onResultadoFecha(GregorianCalendar fecha) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        this.fecha = fmt.format(fecha.getTime());
        binding.tFecha.setText(this.fecha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bFecha:
                DialogFecha df=new DialogFecha();
                df.show(getSupportFragmentManager(), "Mi dialogo fecha");
                break;
            case R.id.bHora:
                DialogHora dt=new DialogHora();
                dt.show(getSupportFragmentManager(), "Mi dialogo hora");
                break;
            case R.id.bCrear:
                bCrearMethod();
                break;
            case R.id.bCancelar:
                finish();
                break;
        }
    }

    @Override
    public void onResultadoHora(int i, int i2) {
        this.hora = ""+i+":"+i2;
        binding.tHora.setText(this.hora.trim());
    }
    public ArrayList<String> nombres(){
        ArrayList<String> dev = null;
        dev = new ArrayList<>();
        for(Local l: MainActivity.listLocales){
            dev.add(l.getName());
        }
        this.nombreLocal = dev.get(0);
        return dev;
    }
    public void spinnerSet(){
        ArrayAdapter<String> adaptador;
        Spinner sp = binding.spinnerLocales;
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombres());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adaptador);
        sp.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner sp = binding.spinnerLocales;
        this.nombreLocal = sp.getSelectedItem().toString();
        System.out.println("nombreLocal = " + nombreLocal);
        Local l = MainActivity.listLocales
                .stream()
                .filter(local -> local.getName().equalsIgnoreCase(nombreLocal)).findFirst().orElse(null);
        this.phone = l.getPhone();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void bCrearMethod(){
        Booking booking = null;
        booking = new Booking();
        String dateToday = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        booking.setMadeDateBooking(dateToday);

        if(this.fecha.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Especifica una fecha", Toast.LENGTH_LONG).show();
        }else{
            booking.setDateBooking(this.fecha);
            if(this.hora.equalsIgnoreCase("")){
                Toast.makeText(getApplicationContext(), "Especifica una hora", Toast.LENGTH_LONG).show();
            }else{
                booking.setLocalName(this.nombreLocal);
                booking.setHora(this.hora);
                booking.setPhone(this.phone);
                booking.setPeople(Integer.parseInt(binding.tNumeroPersonas.getText().toString()));

                System.out.println("booking.toString() = " + booking.toString());
                saveInBBDD(booking);
                MainActivity.addBooking(booking);
                finish();
            }
        }

    }
    public void saveInBBDD(Booking book){
        database = openOrCreateDatabase("gestionReservas", Context.MODE_PRIVATE, null);
        String consulta = "INSERT INTO bookings VALUES ('"+book.getLocalName()+"', '"+book.getPhone()+"','"+book.getDateBooking()+"','"+book.getMadeDateBooking()+"',"+book.getPeople()+",'"+book.getHora()+"')";
        database.execSQL(consulta);
    }

}