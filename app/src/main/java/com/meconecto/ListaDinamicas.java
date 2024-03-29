package com.meconecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.meconecto.data.Actividad;
import com.meconecto.data.AppConfiguration;
import com.meconecto.data.Categoria;
import com.meconecto.data.GameDataFac;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.ActivityListaDinamicasBinding;
import com.meconecto.ui.home.HomeViewModel;
import com.meconecto.ui.modals.Modal1;
import com.meconecto.ui.modals.Modal3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ListaDinamicas extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    private AppBarConfiguration appBarConfiguration;
    private ActivityListaDinamicasBinding binding;

    private FirstFragmentModel firstViewModel;

    private String userId;

    private UserGameData userGData;

    private Categoria appC;

    private FirebaseDatabase mData;

    private SharedPreferences localPrefs =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        binding = ActivityListaDinamicasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (mData == null) {
            mData = FirebaseDatabase.getInstance();
        }
        localPrefs = getSharedPreferences(MainActivity.APP_NAME, MODE_PRIVATE);

        setSupportActionBar(binding.toolbar);

        userId = getIntent().getStringExtra(MainActivity.APP_USERID);
        appC = (Categoria) getIntent().getSerializableExtra(MainActivity.APP_CONFIG);
        String completedActivs = (String) getIntent().getSerializableExtra(MainActivity.APP_COMPLETED);

        firstViewModel = new ViewModelProvider(this).get(FirstFragmentModel.class);
        firstViewModel.setCompletedActivs(completedActivs);
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
        ponerTitulo();
        if (localPrefs.getBoolean(appC.getNombre()+"firstrun", true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.mirarvideos)
                    .setPositiveButton(R.string.btnOk, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // START THE GAME!
                        }
                    });

            // Create the AlertDialog object and return it
            builder.create().show();
            localPrefs.edit().putBoolean(appC.getNombre()+"firstrun", false).commit();
        }
    }

    public void ponerTitulo(){
        getSupportActionBar().setTitle(appC.getNombre());
    }

    public void checkLogros(){
        if(userGData!=null) {
            ArrayList<String> ids = new ArrayList<>();
            for (Map.Entry<String, Actividad> entry : appC.getActividades().entrySet()) {
                ids.add(entry.getValue().getId());
            }
            ArrayList<String> newArrLogros = userGData.checkLogros(ids);
            ArrayList<String> currentLogros = userGData.getArrLogros();
            newArrLogros.removeAll(currentLogros);
            if (newArrLogros.size() > 0 && newArrLogros.get(0)!="") {  //Si hay nuevos logros entonces hay mas de 0
                Log.i("meconecto:","nuevo logro");
                Log.i("meconecto:",newArrLogros.get(0));
                System.out.println(newArrLogros);
                Modal3 m = new Modal3();
                m.setDataLogro(userGData.getLogroData(newArrLogros.get(0)));
                m.setCerrarClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.dismiss();
                    }
                });
                m.show(this.getSupportFragmentManager(), Modal1.TAG);
                String logros = currentLogros.toString();
                logros = logros.replace("[", "")
                        .replace("]", "")
                        .replace(" ", "");
                String lg2 =newArrLogros.toString();
                lg2 = lg2.replace("[", "")
                        .replace("]", "")
                        .replace(" ", "");
                userGData.setLogros(logros+","+lg2);
                GameDataFac.setUserGameData(mData,userId,userGData);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(appC.getNombre());
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_lista_dinamicas);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void cargarUsuario() {
        GameDataFac.cargaDataUsuario(mData,userId, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                Log.i("meconecto:","Se cargara la config del juego en listas---");
                if (dataSnapshot.exists()) {      //Ya existe la data del usuario
                    userGData = dataSnapshot.getValue(UserGameData.class);
                    firstViewModel.setCompletedActivs(userGData.getActividadesCompletadas());
                }
                Log.i("meconecto:","---Se cargo la config del juego en listas");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Log.i("meconecto:","Sucedió un error con la bd al cargar el usuario");
            }
        });
    }

    public void updateUserGameData(Long punteo,String activId){
        Log.i("meconecto:","actualizara el punteo del usuario");
        userGData.sumarPuntos(punteo);
        Boolean cambio = userGData.evaluarNivel();
        if(cambio){
            Log.i("meconecto:","cambio de nivel");
            Toast.makeText(this.getBaseContext(), R.string.cambioNivel,Toast.LENGTH_LONG);
        }
        userGData.addCompleted(activId);
        GameDataFac.setUserGameData(mData,userId,userGData);
        Log.i("meconecto:","Actualizco el punto");
    }

    private void mostrarLogro(){

    }


}