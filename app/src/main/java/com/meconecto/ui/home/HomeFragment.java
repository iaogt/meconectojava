package com.meconecto.ui.home;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.meconecto.FirstFragment;
import com.meconecto.ListaDinamicas;
import com.meconecto.MainActivity;
import com.meconecto.R;
import com.meconecto.data.Avatar;
import com.meconecto.data.GameDataFac;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.FragmentHomeBinding;
import com.meconecto.ui.ranking.RankingFragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class HomeFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;

    private FragmentHomeBinding binding;

    UserGameData uGD;

    TextView labelPunteo;

    ImageView avatar;
    Avatar nomAvatar;
    Long punteo;
    ImageButton btn2, btn3;
    private MediaPlayer mpBack;
    HomeViewModel homeViewModel;
    Boolean click2;
    Boolean click3;


    class PunteoObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            uGD = (UserGameData)o;
            Avatar a = new Avatar();
            a.setNombre(uGD.getNomAvatar());
            a.setImgAvatar(uGD.getAvatar());
            nomAvatar = a;
            punteo = uGD.getPunteo();
            updateHomeScreen();
        }
    }

    class AvatarObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            nomAvatar = (Avatar)o;
            Log.i("meconecto:","cambio avatar");
            updateHomeScreen();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, HomeFragment.this.getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, HomeFragment.this.getClass().getSimpleName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView logo = (ImageView)binding.imageView;
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://meconectosinclavos.net.gt"));
                startActivity(browserIntent);
            }
        });
        avatar = (ImageView) binding.imageView4;
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("avatarName",nomAvatar.getNombre());
                NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_navigation_home_to_selectAvatar,b);
            }
        });
        btn2 = (ImageButton)binding.imageButton2;
        btn3 = (ImageButton)binding.imageButton3;
        btn2.setAlpha(Float.parseFloat("0.5"));
        //btn2.setEnabled(false);
        click2=false;
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!click2) {
                   ((MainActivity) requireActivity()).enviarAListado2(v);
               }else{
                   Toast t = Toast.makeText(getContext(),"Complete actividades del nivel anterior primero",Toast.LENGTH_LONG);
                   t.show();
               }
            }
        });
        btn3.setAlpha(Float.parseFloat("0.5"));
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!click3) {
                    ((MainActivity) requireActivity()).enviarAListado3(v);
                }else{
                    Toast t = Toast.makeText(getContext(),"Complete actividades del nivel anterior primero",Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
        click3=false;

        //labelPunteo = binding.textView2;
        homeViewModel.getuserGData().observe(getViewLifecycleOwner(), new PunteoObserver());
        homeViewModel.getAvatar().observe(getViewLifecycleOwner(), new AvatarObserver());

        //updateHomeScreen();
        return root;
    }


    public void updateHomeScreen(){
        List arrAvatar;
        binding.textView13.setText(nomAvatar.getNombre());
        switch(nomAvatar.getImgAvatar()){
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
            case "luisa":{
                arrAvatar = UserGameData.getAvatar5();
                break;
            }
            case "mama":{
                arrAvatar = UserGameData.getAvatar6();
                break;
            }
            case "papa":{
                arrAvatar = UserGameData.getAvatar7();
                break;
            }
            case "abuelo":{
                arrAvatar = UserGameData.getAvatar8();
                break;
            }
            default:{
                Log.i("meconecto:","usara el default");
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
        Log.i("meconecto:","wowow:");
        Log.i("meconecto:",nomAvatar.getImgAvatar());
        binding.imageView4.setImageResource((int)arrAvatar.get(niveles.get(uGD.nivel)));
        if(niveles.get(uGD.nivel)>=1){
            btn2.setAlpha(Float.parseFloat("1.0"));
            //btn2.setEnabled(true);
            click2=true;
        }
        if(niveles.get(uGD.nivel)>=4){
            btn3.setAlpha(Float.parseFloat("1.0"));
            //btn3.setEnabled(true);
            click3=true;
        }

        //labelPunteo.setText(uGD.punteo.toString()+" puntos");
    }

    public void selectAvatar(){
        Bundle b = new Bundle();
        b.putString("avatarName",nomAvatar.getNombre());
        NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.action_navigation_home_to_selectAvatar,b);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}