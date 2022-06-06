package com.example.estimotedrawer.ui.reservas;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estimotedrawer.MainActivity;
import com.example.estimotedrawer.R;
import com.example.estimotedrawer.addBooking.AddBookingActivity;
import com.example.estimotedrawer.databinding.FragmentReservasBinding;
import com.example.estimotedrawer.models.Booking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservasFragment extends Fragment implements View.OnClickListener {
    private  RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter myAdapter;
    private FragmentReservasBinding binding;
    private BluetoothAdapter bluetoothAdapter;
    private Booking.onDeleteBooking odb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup bookainer, Bundle savedInstanceState) {
        binding = FragmentReservasBinding.inflate(inflater, bookainer, false);
        View root = binding.getRoot();

        //inflarRecycle(MainActivity.listaBookings);
        inflarRecycle(outOfDateBooking());

        binding.bSumar.setOnClickListener(this);
        return root;
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        odb=(Booking.onDeleteBooking) context;
    }

    public void inflarRecycle(ArrayList<Booking> reservas){
        //RECYCLE
        binding.recycleRerservas.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        binding.recycleRerservas.setLayoutManager(layoutManager);
        // specify an adapter
        myAdapter = new MyAdapterBooking(reservas, odb);
        binding.recycleRerservas.setAdapter(myAdapter);
        registerForContextMenu(binding.recycleRerservas);
        /*
         * FIN CREACIÓN DEL RecyclerView
         */
    }

    @Override
    public void onResume() {
        super.onResume();
        inflarRecycle(MainActivity.listaBookings);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bSumar:
                Intent i = new Intent(getContext(), AddBookingActivity.class);
                startActivity(i);
                break;
        }
    }
    public void bluetoothChecker(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(getContext(), "Dispositivo sin bluetooth", Toast.LENGTH_LONG).show();
        }else if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }
    private ArrayList<Booking> outOfDateBooking(){
        ArrayList<Booking> dev = new ArrayList<Booking>();

        for(Booking b: MainActivity.listaBookings){
            Date current = new Date();
            //create date object

            Date prev = null;
            try {
                System.out.println("fecha -- "+b.getDateBooking());
                prev = new SimpleDateFormat("dd-MM-yyyy").parse(b.getDateBooking());

                if(prev.before(current)){
                    dev.add(b);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            //compare both dates

        }
        return dev;
    }

}