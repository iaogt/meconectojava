package com.meconecto.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meconecto.databinding.FragmentDashboardBinding;
import com.meconecto.ui.amigos.AmigosListAdapter;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    RecyclerView lista;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> logros;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lista = binding.listaLogros;

        GridLayoutManager glm = new GridLayoutManager(this.getContext(),3);

        lista.setLayoutManager(glm);

        logros = new ArrayList<>();
        logros.add("Logro 1");
        logros.add("Logro 2");
        logros.add("Logro 3");
        logros.add("Logro 4");
        logros.add("Logro 5");
        logros.add("Logro 6");
        logros.add("Logro 7");
        logros.add("Logro 8");

        lista.setAdapter(new LogrosAdapter(logros, new LogrosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String a) {
                System.out.println("Dio click a la fila "+a);
            }
        }));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}