package com.example.estimotedrawer.ui.home;

import android.annotation.SuppressLint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estimotedrawer.R;
import com.example.estimotedrawer.models.Local;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Local> listaDatos;
    private Local.onLocalSeleccionad obs;
    private Local.onLocalNumberPhone oln;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final TextView nombre;
        private final TextView aforo;
        private  final CardView cardView;
        private final Button b1;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            cardView = view.findViewById(R.id.mCardView);
            cardView.setOnCreateContextMenuListener(this);

            nombre = view.findViewById(R.id.tNombre);
            aforo =view.findViewById(R.id.tAforo);
            b1=view.findViewById(R.id.bEliminar);
           // b1.setOnClickListener(this);
        }

        //getter

        public TextView getNombre() {
            return nombre;
        }

        public TextView getAforo() {
            return aforo;
        }

        public Button getB1() {
            return b1;
        }


    //Menu flotante opciones
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("titulo");
            menu.add(this.getAdapterPosition(), 120,0,"Ver menu");
            menu.add(this.getAdapterPosition(), 121,0,"Llamar");
        }
    }//fin view holder


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    //contructor donde recibe por parametro los datos que queremos mostrar
    public MyAdapter(ArrayList<Local> dataSet, Local.onLocalSeleccionad obs, Local.onLocalNumberPhone oln) {
        this.listaDatos = dataSet;
        this.obs = obs;
        this.oln = oln;
        
    }

    // Create new views (invoked by the layout manager)

    //seguir aqui, para que nos cree la vista que vamos a tratar a continuacion.Inflar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)


    //cada vez que movemos la lista, se crea una llamada a este metodo.
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        viewHolder.getNombre().setText(listaDatos.get(position).getName());
        viewHolder.getAforo().setText(String.format("%.2f",listaDatos.get(position).getCapacityPorcentage())+" %");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaDatos.size();
    }
    //opciones
    public void optionMenu(int position){
        obs.onResultadoLocal(listaDatos.get(position).getUrlMenu());
    }
    public void optionPhone(int position){
        oln.onResultadoNumberphone2(listaDatos.get(position).getPhone());
    }


}


