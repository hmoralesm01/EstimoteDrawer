package com.example.estimotedrawer.reviews;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.estimotedrawer.MainActivity;
import com.example.estimotedrawer.R;
import com.example.estimotedrawer.models.Booking;
import com.example.estimotedrawer.models.Review;

import java.util.ArrayList;

public class MyAdapterReview extends RecyclerView.Adapter<MyAdapterReview.ViewHolder>{

    private ArrayList<Review> listaDatos;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView comentario;
        private ImageView img;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            comentario = view.findViewById(R.id.tComment);
            img = view.findViewById(R.id.imgReview);
        }
        //getter


        public TextView getComentario() {
            return comentario;
        }

        public ImageView getImg() {
            return img;
        }
    }//fin view holder

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    //contructor donde recibe por parametro los datos que queremos mostrar
    public MyAdapterReview(ArrayList<Review> dataSet) {
        listaDatos = dataSet;
    }

    // Create new views (invoked by the layout manager)

    //seguir aqui, para que nos cree la vista que vamos a tratar a continuacion.Inflar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_item_review, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)


    //cada vez que movemos la lista, se crea una llamada a este metodo.
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        viewHolder.getComentario().setText(listaDatos.get(position).getComment());

        if(listaDatos.get(position).getImg()==3){
            viewHolder.getImg().setImageResource(R.drawable.caranegativo);
        }else if(listaDatos.get(position).getImg()==2){
            viewHolder.getImg().setImageResource(R.drawable.carapositivo);
        }else{
            viewHolder.getImg().setImageResource(R.drawable.caraseria);
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaDatos.size();
    }


}


