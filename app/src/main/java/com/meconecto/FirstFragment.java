package com.meconecto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.meconecto.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;

    private FragmentFirstBinding binding;
    RecyclerView lista;
    RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, FirstFragment.this.getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, FirstFragment.this.getClass().getSimpleName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        layoutManager = new LinearLayoutManager(getContext());

        lista = binding.listadinamicas;
lista.setLayoutManager((layoutManager));
        String[] datos = {"Título de primera actividad","Título de segunda actividad"};


        lista.setAdapter(new CustomAdapter(datos, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                System.out.println("Dio click a la fila "+item);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        }));
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /*binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}