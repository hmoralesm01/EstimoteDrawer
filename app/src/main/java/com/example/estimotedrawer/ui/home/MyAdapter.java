package com.example.estimotedrawer.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estimotedrawer.R;
import com.example.estimotedrawer.models.Local;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Local> listaDatos;
    private Local.onLocalSeleccionad obs;
    private Local.onLocalNumberPhone oln;
    private Local.onLocalReview olr;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final TextView nombre;
        private final TextView porcentaje;
        private final TextView telefono;
        private  final CardView cardView;
        private ImageView img;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            cardView = view.findViewById(R.id.mCardView);
            cardView.setOnCreateContextMenuListener(this);
            nombre = view.findViewById(R.id.tNombre);
            telefono = view.findViewById(R.id.tTelefono);
            porcentaje =view.findViewById(R.id.tPocertanje);
            img = view.findViewById(R.id.imgLocal);
        }

        //getter

        public TextView getNombre() {
            return nombre;
        }

        public TextView getPorcentaje() {
            return porcentaje;
        }

        public TextView getTelefono() {
            return telefono;
        }

        public ImageView getImg() {
            return img;
        }

        //Menu flotante opciones
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opciones");
            menu.add(this.getAdapterPosition(), 120,0,"Ver menu");
            menu.add(this.getAdapterPosition(), 121,0,"Llamar");
            menu.add(this.getAdapterPosition(), 122,0,"Info");
        }
    }//fin view holder


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    //contructor donde recibe por parametro los datos que queremos mostrar
    public MyAdapter(ArrayList<Local> dataSet, Local.onLocalSeleccionad obs, Local.onLocalNumberPhone oln, Local.onLocalReview olr){
        this.listaDatos = dataSet;
        this.obs = obs;
        this.oln = oln;
        this.olr = olr;
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
        viewHolder.getPorcentaje().setText(String.format("%.2f",listaDatos.get(position).getCapacityPorcentage())+" %");
        viewHolder.getTelefono().setText(listaDatos.get(position).getPhone());

        //img
        StorageReference mStorageReference = null;
        try {
            mStorageReference= FirebaseStorage.getInstance().getReferenceFromUrl(listaDatos.get(position).getPhotoURL());
        }catch (IllegalArgumentException e2){
            mStorageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://bicis2.appspot.com/bike06.jpg");
            System.out.println("Error al cargar la foto, se pone la default");
        }
        try{
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            final File localFile=File.createTempFile("PNG_"+timeStamp,".png");
            mStorageReference.getFile(localFile).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            //https://code.tutsplus.com/es/tutorials/firebase-for-android-file-storage--cms-27376
                            //Insert the downloaded image in its right position at the ArrayList
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            viewHolder.getImg().setImageBitmap(bitmap);
                        }
                    });
        }
        catch (IOException e){
            e.printStackTrace();
        }
        //fin img
        //fin img
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
    public void optionReview(int position){
        olr.onResultadoLocalReview(listaDatos.get(position).getUrlToGoogle());
    }


}


