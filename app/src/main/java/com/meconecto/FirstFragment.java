package com.meconecto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.meconecto.data.Actividad;
import com.meconecto.data.AppConfiguration;
import com.meconecto.data.Categoria;
import com.meconecto.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;

    private FirstFragmentModel firstViewModel;

    private FragmentFirstBinding binding;
    RecyclerView lista;
    RecyclerView.LayoutManager layoutManager;

    private List<Actividad> datos;


    class DatosObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            Categoria c = (Categoria) o;
            for(Actividad a: c.getActividades().values()){
                datos.add(a);
            }
        }
    }

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

        datos = new ArrayList<>();

        firstViewModel = new ViewModelProvider(requireActivity()).get(FirstFragmentModel.class);
        firstViewModel.getCategory().observe(getViewLifecycleOwner(),new DatosObserver());

        lista = binding.listadinamicas;
        lista.setLayoutManager((layoutManager));

        lista.setAdapter(new CustomAdapter(datos, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Actividad a) {
                System.out.println("Dio click a la fila "+a.getTitulo());
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