package com.meconecto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meconecto.data.AppConfiguration;
import com.meconecto.data.ConfigFactory;
import com.meconecto.data.GameDataFac;
import com.meconecto.data.MyUserFactory;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.ActivityMainBinding;
import com.meconecto.ui.home.HomeViewModel;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    private ActivityMainBinding binding;

    private AppConfiguration config;

    private UserGameData userGData;
    private MyUserFactory userFac;
    private String userId="";

    private Boolean cargoConfig=false;
    private Boolean cargoUser = false;
    private Boolean cargoUserData = false;

    private HomeViewModel homeViewModel;

    public static final String APP_CONFIG = "com.meconecto.APP_CONFIG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        userFac = new MyUserFactory(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null) {
                    cargarUsuario();
                }
            }
        });


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.amigosFragment, R.id.navigation_dashboard, R.id.rankingFragment,R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        if(userId!=null){   //Si hay usuario
            this.cargarUsuario();
        }else{
            userFac.authUserAnonymous(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                    } else {

                    }
                }
            });
        }
        this.loadJSONConfig();
    }

    public void cargarUsuario(){
        GameDataFac.cargaDataUsuario(userId,new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                System.out.println("Se cargara la config---");
                if(dataSnapshot.exists()){      //Ya existe la data del usuario
                    userGData = dataSnapshot.getValue(UserGameData.class);
                }else{
                    userGData = GameDataFac.emptyGame();
                    GameDataFac.setUserGameData(userId,userGData);
                }
                refrescaHome();
                System.out.println("---Se cargo la config");
                cargoUserData=true;
                quitarCargador();
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                System.out.println("Sucedió un error con la bd al cargar el usuario");
            }
        });
    }

    public void refrescaHome(){
        homeViewModel.setmText(userGData);
        System.out.println("Actualiza punteo:");
        System.out.println(userGData.punteo.toString());
    }

    public void enviarAListado(View view){
        Intent intent = new Intent(view.getContext(), ListaDinamicas.class);
        intent.putExtra(APP_CONFIG,config.getCategory("cyberbullying"));
        startActivity(intent);
    }

    public void loadJSONConfig(){
        // [START post_value_event_listener]
        ConfigFactory.loadConfiguration(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                System.out.println("Se cargara la config---");
                config = ConfigFactory.buildConfiguration((Map<String,Object>) dataSnapshot.getValue());
                System.out.println("---Se cargo la config");
                cargoConfig=true;
                quitarCargador();
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                System.out.println("Sucedió un error con la bd");
            }
        });

    }

    private void quitarCargador(){
        if(cargoConfig&&cargoUserData){
            ImageView cargador = findViewById(R.id.cargador);
            cargador.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //updateUI(currentUser);
        userId = userFac.getCurrentUser();
    }

}