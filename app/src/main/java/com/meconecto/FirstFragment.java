package com.meconecto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private String completedActivs="";

    private CustomAdapter adapterActividades;

    Categoria c;


    class DatosObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            System.out.println("Vinieron las cats");
            c = (Categoria) o;
            for(Actividad a: c.getActividades().values()){
                if(completedActivs.indexOf(a.getId())>0){
                    a.setCompletada(true);
                }
                datos.add(0,a);
            }
            TextView txtSubtitle = binding.textView6;
            txtSubtitle.setText(c.getSubtitle());
        }
    }

    class DatosObserver2 implements Observer {
        @Override
        public void onChanged(Object o) {
            System.out.println("vinieron las completadas");
            String d = (String) o;
            completedActivs = d;
            if(datos.size()>0){
                List<Actividad> temp = new ArrayList<>();
                for(Actividad a: datos){
                    if(completedActivs.indexOf(a.getId())>0){
                        a.setCompletada(true);
                    }
                    temp.add(a);
                }
                datos = temp;
                adapterActividades.notifyDataSetChanged();
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
        firstViewModel.getCompletedActivs().observe(getViewLifecycleOwner(),new DatosObserver2());
        firstViewModel.getCategory().observe(getViewLifecycleOwner(),new DatosObserver());

        lista = binding.listadinamicas;
        lista.setLayoutManager((layoutManager));

        Button btnVideo = binding.btnVervideo;
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(c.getVideourl()));
                startActivity(browserIntent);
            }
        });

        adapterActividades = new CustomAdapter(datos, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Actividad a) {
                firstViewModel.setSelectedActivity(a);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        lista.setAdapter(adapterActividades);

        return binding.getRoot();

    }


    @Override
    public void onResume(){
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        adapterActividades.notifyDataSetChanged();

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("CAtgro");

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