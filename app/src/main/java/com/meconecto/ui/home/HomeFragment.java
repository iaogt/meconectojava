package com.meconecto.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.meconecto.FirstFragment;
import com.meconecto.ListaDinamicas;
import com.meconecto.MainActivity;
import com.meconecto.R;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.FragmentHomeBinding;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    UserGameData uGD;

    TextView labelPunteo;

    class PunteoObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            uGD = (UserGameData)o;
            updateHomeScreen();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView avatar = (ImageView) binding.imageView4;
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_navigation_home_to_selectAvatar);
            }
        });

        //labelPunteo = binding.textView2;
        homeViewModel.getuserGData().observe(getViewLifecycleOwner(), new PunteoObserver());

        return root;
    }

    public void updateHomeScreen(){
        switch(uGD.getNivel()){
            case "nivel1":{

                binding.imageView4.setImageResource(R.drawable.avatar1simple);
            }
            default:{
                binding.imageView4.setImageResource(R.drawable.avatar1simple);
            }
        }
        //labelPunteo.setText(uGD.punteo.toString()+" puntos");
    }

    public void selectAvatar(){
        NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.action_navigation_home_to_selectAvatar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}