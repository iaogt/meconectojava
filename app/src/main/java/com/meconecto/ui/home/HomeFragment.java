package com.meconecto.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.meconecto.ListaDinamicas;
import com.meconecto.MainActivity;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView labelPunteo;

    class PunteoObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            UserGameData uGD = (UserGameData)o;
            labelPunteo.setText(uGD.punteo.toString());
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        labelPunteo = binding.textView2;
        homeViewModel.getuserGData().observe(getViewLifecycleOwner(), new PunteoObserver());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}