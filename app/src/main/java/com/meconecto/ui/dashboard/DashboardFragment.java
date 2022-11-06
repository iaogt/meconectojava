package com.meconecto.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.meconecto.R;
import com.meconecto.databinding.FragmentDashboardBinding;
import com.meconecto.ui.amigos.AmigosFragment;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;

    private FragmentDashboardBinding binding;
    RecyclerView lista;
    RecyclerView lista2;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> logros;
    private ArrayList<String> insignias;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, DashboardFragment.this.getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, DashboardFragment.this.getClass().getSimpleName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lista = binding.listaLogros;
        lista2 = binding.listaInsignias;

        GridLayoutManager glm = new GridLayoutManager(this.getContext(),3);
        GridLayoutManager glm2 = new GridLayoutManager(this.getContext(),3);


        lista.setLayoutManager(glm);
        lista2.setLayoutManager(glm2);

        logros = new ArrayList<>();
        logros.add(String.valueOf(R.drawable.logro1));
        logros.add(String.valueOf(R.drawable.logro2));
        logros.add(String.valueOf(R.drawable.logro3));
        logros.add(String.valueOf(R.drawable.logro4));

        insignias = new ArrayList<>();
        insignias.add(String.valueOf(R.drawable.insignia1));
        insignias.add(String.valueOf(R.drawable.insignia1));
        insignias.add(String.valueOf(R.drawable.insignia1));



        lista.setAdapter(new LogrosAdapter(logros, new LogrosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String a) {
                System.out.println("Dio click a la fila "+a);
            }
        }));

        lista2.setAdapter(new LogrosAdapter(insignias, new LogrosAdapter.OnItemClickListener() {
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