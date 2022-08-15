package com.meconecto.ui.amigos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.meconecto.databinding.FragmentAmigosBinding;
import com.meconecto.ui.amigos.AmigosViewModel;

public class AmigosFragment extends Fragment {

    private FragmentAmigosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AmigosViewModel dashboardViewModel =
                new ViewModelProvider(this).get(AmigosViewModel.class);

        binding = FragmentAmigosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
