package com.example.estimotedrawer.ui.reservas;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.estimotedrawer.MainActivity;
import com.example.estimotedrawer.R;
import com.example.estimotedrawer.models.Booking;

import java.util.ArrayList;

public class MyAdapterBooking extends RecyclerView.Adapter<MyAdapterBooking.ViewHolder>{

    private ArrayList<Booking> listaDatos;
    private Booking.onDeleteBooking onDeleteBooking;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombre;
        private final TextView phone;
        private final TextView fecha;
        private final TextView hora;
        private final TextView gente;
        private final Button bEliminar;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            bEliminar = view.findViewById(R.id.bRestarR);
            nombre = view.findViewById(R.id.tNombreLocal);
            phone =view.findViewById(R.id.tPhoneR);
            fecha =view.findViewById(R.id.tFechaR);
            hora =view.findViewById(R.id.tHoraR);
            gente =view.findViewById(R.id.tGenteR);
        }
        //getter


        public Button getbEliminar() {
            return bEliminar;
        }

        public TextView getNombre() {
            return nombre;
        }

        public TextView getPhone() {
            return phone;
        }
        public TextView getFecha() {
            return fecha;
        }
        public TextView getHora() {
            return hora;
        }
        public TextView getGente() {
            return gente;
        }
    }//fin view holder

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    //contructor donde recibe por parametro los datos que queremos mostrar
    public MyAdapterBooking(ArrayList<Booking> dataSet, Booking.onDeleteBooking odb) {
        listaDatos = dataSet;
        onDeleteBooking = odb;
    }

    // Create new views (invoked by the layout manager)

    //seguir aqui, para que nos cree la vista que vamos a tratar a continuacion.Inflar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_item_booking, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)


    //cada vez que movemos la lista, se crea una llamada a este metodo.
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        viewHolder.getNombre().setText(listaDatos.get(position).getLocalName());
        viewHolder.getFecha().setText(listaDatos.get(position).getDateBooking());
        viewHolder.getHora().setText(listaDatos.get(position).getHora());
        viewHolder.getPhone().setText("Tlf: "+listaDatos.get(position).getPhone());
        viewHolder.getGente().setText(""+listaDatos.get(position).getPeople()+" Persona");
        if(listaDatos.get(position).getPeople()>1)
            viewHolder.getGente().setText(""+listaDatos.get(position).getPeople()+" Personas");

        viewHolder.getbEliminar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("position = " + position);
                onDeleteBooking.onResultadoDeletBooking(listaDatos.get(position).getId(), MyAdapterBooking.this, position);
            }
        });

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaDatos.size();
    }


}


