package com.example.estimotedrawer.reviews;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.estimotedrawer.databinding.ActivityMainBinding;
import com.example.estimotedrawer.databinding.ActivityVerReviewsBinding;
import com.example.estimotedrawer.models.Local;
import com.example.estimotedrawer.models.Review;
import com.example.estimotedrawer.ui.home.MyAdapter;

import java.util.ArrayList;

public class VerReviewsActivity extends AppCompatActivity {
    private ActivityVerReviewsBinding binding;
    ArrayList<Review> datos;
    RecyclerView.LayoutManager layoutManager;
    MyAdapterReview myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle getBundle = this.getIntent().getExtras();
        datos = getBundle.getParcelableArrayList("lista");

        inflarRecycle(datos);

    }
    public void inflarRecycle(ArrayList<Review> reviews){
        //RECYCLE
        binding.recycleReviews.setHasFixedSize(true);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recycleReviews.setLayoutManager(layoutManager);

        // specify an adapter
        myAdapter = new MyAdapterReview(reviews);


        binding.recycleReviews.setAdapter(myAdapter);
        registerForContextMenu(binding.recycleReviews);
        /*
         * FIN CREACIÓN DEL RecyclerView
         */
    }
}