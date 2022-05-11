package com.example.estimotedrawer.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.estimotedrawer.databinding.FragmentSlideshowBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        closeApp();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void closeApp() {
        // Firebase sign out
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this.getContext(), "Hasta luego "+mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
        mAuth.signOut();
        System.exit(0);

    }
}