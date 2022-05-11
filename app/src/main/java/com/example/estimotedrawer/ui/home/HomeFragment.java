package com.example.estimotedrawer.ui.home;

import static com.estimote.coresdk.common.config.EstimoteSDK.getApplicationContext;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estimotedrawer.databinding.FragmentHomeBinding;
import com.example.estimotedrawer.models.Estimote;
import com.example.estimotedrawer.models.Local;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment{

    private FragmentHomeBinding binding;
    private ArrayList<Local> listaLocales;
    private DatabaseReference mDatabase;

    RecyclerView.LayoutManager layoutManager;
    MyAdapter myAdapter;

    Local.onLocalSeleccionad obs;
    Local.onLocalNumberPhone oln;
    onPersistenciaDatos inter;


    //para estar en contacto con el MainActivity y poder transferir datos.
    public void onAttach(Context context) {
        super.onAttach(context);
        obs=(Local.onLocalSeleccionad)context;
        oln=(Local.onLocalNumberPhone)context;
        inter=(onPersistenciaDatos) context;

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        objetoLista();

        //filtrar
        binding.tFiltro.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                inflarRecycle(listar(binding.tFiltro.getText().toString()));
                return true;
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        mDatabase = null;
        listaLocales = null;
    }
    public void objetoLista(){
        mDatabase.child("locales_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaLocales = new ArrayList<>();
                for (DataSnapshot pro : snapshot.getChildren()) {
                    Local b = new Local();
                    b.setPhone(String.valueOf(pro.child("phone").getValue()));
                    b.setName(String.valueOf(pro.child("name").getValue()));
                    b.setUrlMenu(String.valueOf(pro.child("urlMenu").getValue()));
                    b.setUUID(String.valueOf(pro.child("UUID").getValue()));
                    b.setCapacityActual(Math.toIntExact((long)pro.child("capacityActual").getValue()));
                    b.setCapacityMax(Math.toIntExact((long)pro.child("capacityMax").getValue()));

                    //d6714228-7bbb-41fc-91e3-24e6aadd3703

                    b.setCapacityPorcentage((double)(b.getCapacityActual()*100)/b.getCapacityMax());

                    if(pro.hasChild("listEstimotes")){
                        for(DataSnapshot pr : pro.getChildren()){//recorre atributos
                            if(pr.getKey().equalsIgnoreCase("listEstimotes")){
                                for(DataSnapshot pr2 : pr.getChildren()) {//recorre atributos
                                    Estimote estimote = new Estimote();
                                    estimote.setMinor(Math.toIntExact((long) pr2.child("minor").getValue()));
                                    estimote.setMajor(Math.toIntExact((long) pr2.child("major").getValue()));
                                    b.addEstimote(estimote);
                                }
                            }
                        }
                    }
                    System.out.println("b.toString() = " + b.toString()+"  UUID"+b.getUUID());
                    listaLocales.add(b);
                }
                //Mandar la lista al main
                try {
                    //Mandar la lista al main
                    inter.onPersistencia(listaLocales);
                    inflarRecycle(listaLocales);
                    /*
                     * FIN CREACIÓN DEL RecyclerView
                     */
                }catch (Exception e){
                    System.err.println("CARGA del recycle");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"ERROR, no se ha podido cargar los locales", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public interface onPersistenciaDatos{
        void onPersistencia(ArrayList<Local> lista);
    }

    public ArrayList<Local> listar(String filtro){
        ArrayList<Local> dev = new ArrayList<>();

        if(filtro.length()>0){
            for(Local l: listaLocales){
                if(l.getName().toLowerCase(Locale.ROOT).contains(filtro.toLowerCase(Locale.ROOT))){
                    dev.add(l);
                }
            }
        }else{
            dev=listaLocales;
        }
        return dev;

    }

    public void inflarRecycle(ArrayList<Local> locales){
        //RECYCLE
        binding.recyclerViewHome.setHasFixedSize(true);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewHome.setLayoutManager(layoutManager);

        // specify an adapter
        myAdapter = new MyAdapter(locales, obs, oln);


        binding.recyclerViewHome.setAdapter(myAdapter);
        registerForContextMenu(binding.recyclerViewHome);
        /*
         * FIN CREACIÓN DEL RecyclerView
         */
    }

    //opciones del menu que se han declarado en el adapter
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 120:
                myAdapter.optionMenu(item.getGroupId());
                return true;
            case 121:
                myAdapter.optionPhone(item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }
}//https://www.youtube.com/watch?v=fl5BB3I3MvQ para el menu del recycle