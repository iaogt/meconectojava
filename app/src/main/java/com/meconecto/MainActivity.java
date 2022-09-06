package com.meconecto;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.meconecto.data.AppConfiguration;
import com.meconecto.data.ConfigFactory;
import com.meconecto.data.GameDataFac;
import com.meconecto.data.MyUserFactory;
import com.meconecto.data.Url4Download;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.ActivityMainBinding;
import com.meconecto.ui.amigos.AmigosFragment;
import com.meconecto.ui.amigos.AmigosViewModel;
import com.meconecto.ui.home.HomeFragment;
import com.meconecto.ui.home.HomeViewModel;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

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
    private AmigosViewModel amigosViewModel;

    public static final String APP_CONFIG = "com.meconecto.APP_CONFIG";
    public static final String APP_USERID = "com.meconecto.APP_USERID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }
                        System.out.println("Venia de un deeplink");
                        System.out.println(deepLink);
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("fallo en dynamiclink");
                    }
                });



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
                R.id.navigation_home, R.id.navigation_amigos, R.id.navigation_dashboard, R.id.navigation_ranking,R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        amigosViewModel = new ViewModelProvider(this).get(AmigosViewModel.class);

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
        homeViewModel.setUserId(userId);
        System.out.println("Actualiza punteo:");
        System.out.println(userGData.punteo.toString());
    }

    public void enviarAListado(View view){
        Intent intent = new Intent(view.getContext(), ListaDinamicas.class);
        intent.putExtra(APP_CONFIG,config.getCategory("cyberseguridad"));
        intent.putExtra(APP_USERID,userId);
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
                downloadActivities();
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

    private void downloadActivities(){
        if(config!=null){
            ArrayList<Url4Download> al = config.getUrls();
            downloadAndSave(al);

        }
        quitarCargador();
    }

    public void downloadAndSave(ArrayList<Url4Download> al){
        Url4Download myurl = al.remove(al.size()-1);
        try {
            FileOutputStream fos = this.getBaseContext().openFileOutput(myurl.getID() + "_content.html", Context.MODE_PRIVATE);
            readOnlineFile(myurl.getUrl(), fos, myurl.getID());
            if(al.size()>0)
                downloadAndSave(al);
        }catch(FileNotFoundException e){
            System.out.println("Error al abrir el archivo");
        }
    }

    private void readOnlineFile(String strUrl,FileOutputStream dir,String actId){
        new Thread(new Runnable(){

            public void run(){
                String finalStr="";


                try {
                    // Create a URL for the desired page
                    URL url = new URL(strUrl); //My text file location
                    //First open the connection
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(60000); // timing out in a minute

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    //t=(TextView)findViewById(R.id.TextView1); // ideally do this in onCreate()
                    String str;
                    while ((str = in.readLine()) != null) {
                        finalStr+=str+'\n';
                    }
                    dir.write(finalStr.getBytes());
                    dir.close();
                    in.close();
                } catch (Exception e) {
                    System.out.println("Error al grabar archivos");
                    System.out.println(e);
                }

                //since we are in background thread, to post results we have to go back to ui thread. do the following for that

                /*Activity.this.runOnUiThread(new Runnable(){
                    public void run(){
                        t.setText(urls.get(0)); // My TextFile has 3 lines
                    }
                });*/

            }
        }).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //updateUI(currentUser);
        userId = userFac.getCurrentUser();
    }

    @Override
    public void onResume(){
        super.onResume();
        cargarUsuario();
    }

}