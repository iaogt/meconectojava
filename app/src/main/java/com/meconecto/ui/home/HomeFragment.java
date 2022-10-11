package com.meconecto.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.meconecto.data.GameDataFac;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    UserGameData uGD;

    TextView labelPunteo;

    ImageView avatar;
    String nomAvatar;
    Long punteo;
    ImageButton btn2, btn3;

    class PunteoObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            uGD = (UserGameData)o;
            nomAvatar = uGD.getAvatar();
            punteo = uGD.getPunteo();
            updateHomeScreen();
        }
    }

    class AvatarObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            nomAvatar = (String)o;
            System.out.println("cambio avatar");
            System.out.println(nomAvatar);
            updateHomeScreen();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        avatar = (ImageView) binding.imageView4;
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_navigation_home_to_selectAvatar);
            }
        });
        btn2 = (ImageButton)binding.imageButton2;
        btn3 = (ImageButton)binding.imageButton3;
        btn2.setAlpha(Float.parseFloat("0.5"));
        btn2.setEnabled(false);
        btn3.setAlpha(Float.parseFloat("0.5"));
        btn3.setEnabled(false);

        //labelPunteo = binding.textView2;
        homeViewModel.getuserGData().observe(getViewLifecycleOwner(), new PunteoObserver());
        homeViewModel.getAvatar().observe(getViewLifecycleOwner(), new AvatarObserver());

        //updateHomeScreen();
        return root;
    }


    public void updateHomeScreen(){
        List arrAvatar;
        switch(nomAvatar){
            case "susan":{
                arrAvatar = UserGameData.getAvatar1();
                break;
            }
            case "jose":{
                arrAvatar = UserGameData.getAvatar2();
                break;
            }
            case "afro":{
                arrAvatar = UserGameData.getAvatar3();
                break;
            }
            case "ana":{
                arrAvatar = UserGameData.getAvatar4();
                break;
            }
            case "mama":{
                arrAvatar = UserGameData.getAvatar5();
                break;
            }
            case "papa":{
                arrAvatar = UserGameData.getAvatar6();
                break;
            }
            case "abuelo":{
                arrAvatar = UserGameData.getAvatar7();
                break;
            }
            default:{
                System.out.println("usara el default");
                arrAvatar = UserGameData.getAvatar1();
                break;
            }
        }
        HashMap<String,Integer> niveles = new HashMap<String,Integer>();
        niveles.put("nivel1",0);
        niveles.put("nivel2",1);
        niveles.put("nivel3",2);
        niveles.put("nivel4",3);
        niveles.put("nivel5",4);
        niveles.put("nivel6",5);
        niveles.put("nivel7",6);
        System.out.println("wowow");
        binding.imageView4.setImageResource((int)arrAvatar.get(niveles.get(uGD.nivel)));
        if(niveles.get(uGD.nivel)>=1){
            btn2.setAlpha(Float.parseFloat("1.0"));
            btn2.setEnabled(true);
        }
        if(niveles.get(uGD.nivel)>=4){
            btn3.setAlpha(Float.parseFloat("1.0"));
            btn3.setEnabled(true);
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