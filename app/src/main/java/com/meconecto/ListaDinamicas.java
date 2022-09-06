package com.meconecto;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meconecto.data.AppConfiguration;
import com.meconecto.data.Categoria;
import com.meconecto.data.GameDataFac;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.ActivityListaDinamicasBinding;
import com.meconecto.ui.home.HomeViewModel;

public class ListaDinamicas extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    private AppBarConfiguration appBarConfiguration;
    private ActivityListaDinamicasBinding binding;

    private FirstFragmentModel firstViewModel;

    private String userId;

    private UserGameData userGData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        binding = ActivityListaDinamicasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        userId = getIntent().getStringExtra(MainActivity.APP_USERID);
        Categoria appC = (Categoria) getIntent().getSerializableExtra(MainActivity.APP_CONFIG);
        firstViewModel = new ViewModelProvider(this).get(FirstFragmentModel.class);
        firstViewModel.setCategory(appC);
        cargarUsuario();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_lista_dinamicas);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        /*binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setTitle(appC.getNombre());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_lista_dinamicas);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void cargarUsuario() {
        GameDataFac.cargaDataUsuario(userId, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                System.out.println("Se cargara la config del juego en listas---");
                if (dataSnapshot.exists()) {      //Ya existe la data del usuario
                    userGData = dataSnapshot.getValue(UserGameData.class);
                }
                System.out.println("---Se cargo la config del juego en listas");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                System.out.println("Sucedió un error con la bd al cargar el usuario");
            }
        });
    }

    public void updateUserGameData(Long punteo){
        System.out.println("actualizara el punteo del usuario");
        userGData.sumarPuntos(punteo);
        GameDataFac.setUserGameData(userId,userGData);
        System.out.println("Actualizco el punto");
    }


}