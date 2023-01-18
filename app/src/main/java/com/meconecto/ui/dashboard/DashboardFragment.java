package com.meconecto.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.meconecto.R;
import com.meconecto.data.Avatar;
import com.meconecto.data.IconLogro;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.FragmentDashboardBinding;
import com.meconecto.ui.amigos.AmigosFragment;
import com.meconecto.ui.home.HomeFragment;
import com.meconecto.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class DashboardFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;

    private FragmentDashboardBinding binding;
    RecyclerView lista;
    RecyclerView lista2;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<IconLogro> logros;
    private ArrayList<IconLogro> insignias;

    HomeViewModel homeViewModel;

    UserGameData uGD;


    class PunteoObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            uGD = (UserGameData)o;
            updateLogros();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, DashboardFragment.this.getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, DashboardFragment.this.getClass().getSimpleName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        /*DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);*/
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lista = binding.listaLogros;
        lista2 = binding.listaInsignias;

        GridLayoutManager glm = new GridLayoutManager(this.getContext(),3);
        GridLayoutManager glm2 = new GridLayoutManager(this.getContext(),3);


        lista.setLayoutManager(glm);
        lista2.setLayoutManager(glm2);

        logros = new ArrayList<>();
        logros.add(new IconLogro("i",String.valueOf(R.drawable.logro1)));
        logros.add(new IconLogro("i",String.valueOf(R.drawable.logro2)));
        logros.add(new IconLogro("i",String.valueOf(R.drawable.logro3)));
        logros.add(new IconLogro("i",String.valueOf(R.drawable.logro4)));

        insignias = new ArrayList<>();
        insignias.add(new IconLogro("i",String.valueOf(R.drawable.insignia1)));
        insignias.add(new IconLogro("i",String.valueOf(R.drawable.insignia2)));
        //insignias.add(String.valueOf(R.drawable.insignia1));



        lista.setAdapter(new LogrosAdapter(logros, new LogrosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String a) {
                Log.i("meconecto:","Dio click a la fila "+a);
            }
        }));

        lista2.setAdapter(new LogrosAdapter(insignias, new LogrosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String a) {
                Log.i("meconecto:","Dio click a la fila "+a);
            }
        }));

        homeViewModel.getuserGData().observe(getViewLifecycleOwner(), new DashboardFragment.PunteoObserver());


        return root;
    }

    public void updateLogros(){
        insignias.clear();
        String logros = uGD.getLogros();
        String s = "i";
        if(logros.indexOf("logro1")>=0){
            s="a";
        }
        insignias.add(new IconLogro(s,String.valueOf(R.drawable.insignia1)));
        s="i";
        if(logros.indexOf("logro2")>=0){
            s="a";
        }
        insignias.add(new IconLogro(s,String.valueOf(R.drawable.insignia2)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}