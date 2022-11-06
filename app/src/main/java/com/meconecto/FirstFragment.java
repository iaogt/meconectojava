package com.meconecto;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
    private MediaPlayer mpButton;

    private VideoView vw;


    private List<String> lstVideos;

    private ImageButton btnVideo1,btnVideo2,btnVideo3,btnVideo4;



    class DatosObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            System.out.println("Vinieron las cats");
            c = (Categoria) o;
            if(c.getNombre().compareTo("Informado")==0){
                lstVideos = Arrays.asList("android.resource://" + requireActivity().getPackageName() + "/" +R.raw.v6,"http://webymovil.com/svet/videos/7.mp4","http://webymovil.com/svet/videos/8.mp4","http://webymovil.com/svet/videos/9.mp4");
                vw.setVideoURI(Uri.parse(lstVideos.get(0)));
                vw.setBackgroundResource(R.drawable.thumb_videoa);
                btnVideo1.setBackgroundResource(R.drawable.thumb_videoa);
                btnVideo2.setBackgroundResource(R.drawable.thumb_videon);
                btnVideo3.setBackgroundResource(R.drawable.thumb_videoc);
                btnVideo4.setBackgroundResource(R.drawable.thumb_videod);
            }
            HashMap<String,Actividad> listAct = c.getActividades();
            ArrayList<String> sortedKeys
                    = new ArrayList<String>(listAct.keySet());
            Collections.sort(sortedKeys,Collections.reverseOrder());
            // Display the TreeMap which is naturally sorted
            for (String x : sortedKeys) {
                Actividad a = listAct.get(x);
                if(completedActivs.indexOf(a.getId())>0){
                    a.setCompletada(true);
                };
                datos.add(0,a);
            }

            /*TextView txtSubtitle = binding.textView6;
            txtSubtitle.setText(c.getSubtitle());*/
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
                actualizaVideos();
                adapterActividades.notifyDataSetChanged();
            }
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        lstVideos = Arrays.asList("android.resource://" + requireActivity().getPackageName() + "/" +R.raw.videogrooming,"http://webymovil.com/svet/videos/3.mp4","http://webymovil.com/svet/videos/4.mp4","http://webymovil.com/svet/videos/5.mp4");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, FirstFragment.this.getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, FirstFragment.this.getClass().getSimpleName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        layoutManager = new LinearLayoutManager(getContext());

        datos = new ArrayList<>();

        mpButton = MediaPlayer.create(requireContext(), R.raw.soundbutton2);

        firstViewModel = new ViewModelProvider(requireActivity()).get(FirstFragmentModel.class);
        firstViewModel.getCompletedActivs().observe(getViewLifecycleOwner(),new DatosObserver2());
        firstViewModel.getCategory().observe(getViewLifecycleOwner(),new DatosObserver());

        lista = binding.listadinamicas;
        lista.setLayoutManager((layoutManager));



        btnVideo1 = binding.imageButton9;
        btnVideo1.setTag(0);
        btnVideo2 = binding.imageButton10;
        btnVideo2.setTag(1);
        btnVideo3 = binding.imageButton11;
        btnVideo3.setTag(2);
        btnVideo4 = binding.imageButton12;
        btnVideo4.setTag(3);
        vw = (VideoView)binding.videoView2;
        vw.setBackgroundResource(R.drawable.thumb_video2);
        vw.setVideoURI(Uri.parse(lstVideos.get(0)));
        MediaController mediaController = new MediaController(this.getContext());
        mediaController.setAnchorView(vw);
        vw.setMediaController(mediaController);
        mediaController.setMediaPlayer(vw);
        vw.requestFocus();
        vw.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaController.setAnchorView(vw);
            }
        });
        vw.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    vw.setBackground(null);
                    return true;
                }
                return false;
            }
        });
        View.OnClickListener lst = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vw.setBackground(null);
                Integer t = (Integer)v.getTag();
                vw.setVideoURI(Uri.parse(lstVideos.get(t.intValue())));
                vw.start();
            }
        };
        View.OnClickListener lst2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] lst = completedActivs.split(",");
                Integer t = (Integer)v.getTag();
                if(t.intValue()==1){
                    if(lst.length>=1 && !lst[0].isEmpty()){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lstVideos.get(t.intValue())));
                        startActivity(browserIntent);
                    }else{
                        Toast toa = Toast.makeText(getContext(),"Debe completar al menos una actividad",Toast.LENGTH_LONG);
                        toa.show();
                    }
                }
                if(t.intValue()==2){
                    if(lst.length>=3){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lstVideos.get(t.intValue())));
                        startActivity(browserIntent);
                    }else{
                        Toast toa = Toast.makeText(getContext(),"Debe completar al menos 3 actividades",Toast.LENGTH_LONG);
                        toa.show();
                    }
                }
                if(t.intValue()==3){
                    if(lst.length>=5){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lstVideos.get(t.intValue())));
                        startActivity(browserIntent);
                    }else{
                        Toast toa = Toast.makeText(getContext(),"Debe completar al menos 5 actividades",Toast.LENGTH_LONG);
                        toa.show();
                    }
                }
            }
        };
        btnVideo1.setOnClickListener(lst);
        btnVideo2.setOnClickListener(lst2);
        btnVideo3.setOnClickListener(lst2);
        btnVideo4.setOnClickListener(lst2);


        adapterActividades = new CustomAdapter(datos, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Actividad a) {
                mpButton.start();
                firstViewModel.setSelectedActivity(a);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        lista.setAdapter(adapterActividades);

        return binding.getRoot();

    }

    public void actualizaVideos(){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        System.out.println("Se actualizan los videos");
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        String[] lst = completedActivs.split(",");
        System.out.println(lst.length);
        //btnVideo2.setEnabled(false);
        btnVideo2.setAlpha(Float.parseFloat("0.5"));
        //btnVideo3.setEnabled(false);
        btnVideo3.setAlpha(Float.parseFloat("0.5"));
        //btnVideo4.setEnabled(false);
        btnVideo4.setAlpha(Float.parseFloat("0.5"));
        if((lst.length>=1)){
            //btnVideo2.setEnabled(true);
            btnVideo2.setAlpha(Float.parseFloat("1.0"));
        }
        if((lst.length>=3)){
            btnVideo3.setEnabled(true);
            btnVideo3.setAlpha(Float.parseFloat("1.0"));
        }
        if((lst.length>=5)){
            btnVideo4.setEnabled(true);
            btnVideo4.setAlpha(Float.parseFloat("1.0"));
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        adapterActividades.notifyDataSetChanged();

        ((ListaDinamicas) requireActivity()).ponerTitulo();

        ((ListaDinamicas) requireActivity()).checkLogros();

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