package com.meconecto;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
import com.meconecto.data.Avatar;
import com.meconecto.data.ConfigFactory;
import com.meconecto.data.GameDataFac;
import com.meconecto.data.MyUserFactory;
import com.meconecto.data.Nivel;
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

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
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

    private String completedActivs;

    private MediaPlayer mpBack;
    private MediaPlayer mpButton;

    private Boolean muestraVideo = false;

    public static final String APP_CONFIG = "com.meconecto.APP_CONFIG";
    public static final String APP_USERID = "com.meconecto.APP_USERID";
    public static final String APP_COMPLETED = "com.meconecto.APP_COMPLETED";
    public static final String APP_DBREFERENCE = "com.meconecto.APP_DBREFERENCE";
    public static final String APP_NAME = "com.meconecto.APP";

    public FrameLayout capaVideo;
    public VideoView objVideo;

    private ProgressBar progressBar;
    private int intProgresoCarga=0;

    private FirebaseDatabase database;

    private SharedPreferences localPrefs =null;

    class AvatarObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            Avatar nomAvatar = (Avatar)o;
            userGData.setNomAvatar(nomAvatar.getNombre());
            userGData.setImgAvatar(nomAvatar.getImgAvatar());
            GameDataFac.setUserGameData(database,userId,userGData);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localPrefs = getSharedPreferences(APP_NAME,MODE_PRIVATE);
        if(database==null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mpBack = MediaPlayer.create(this, R.raw.loopdrums);
        mpBack.setLooping(true); // Set looping
        mpBack.setVolume(50, 50);

        mpButton = MediaPlayer.create(this, R.raw.soundbutton2);
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        System.out.println("Venia de un deeplink");
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            String path = deepLink.getPath();
                            if(path.indexOf("/addamigo/")>=0){ //para agregar amigo
                                navegarAmigos(path);
                            }
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("fallo en dynamiclink");
                        System.out.println(e);
                    }
                });



        userFac = new MyUserFactory(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                System.out.println("cambio el status del usuario");
                if(firebaseAuth.getCurrentUser() != null) {
                    cargarUsuario();
                }
            }
        });


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressBar = binding.progressBar;
        capaVideo = binding.videoLoader;
        objVideo = binding.firstVideo;


        this.getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
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

        homeViewModel.getAvatar().observe(this, new MainActivity.AvatarObserver());


        if(userId!=null && userId.trim()!=""){   //Si hay usuario
            System.out.println("Si hay usuario");
            System.out.println(userId);
            this.cargarUsuario();
        }else {
            System.out.println("No hay usuario");
            userFac.authUserAnonymous(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        userId = userFac.getCurrentUser();
                        cargarUsuario();
                    } else {
                        System.out.println("error al cargar usuario anonimo");
                    }
                }
            });
        }
        this.addLoadProgress(10);
        this.loadJSONConfig();
    }

    public void navegarAmigos(String p){
        NavController nc = Navigation.findNavController(this,R.id.nav_host_fragment_activity_main);
        nc.navigate(R.id.navigation_amigos);
        amigosViewModel.setNuevoAmigo(p.replace("/addamigo/",""));
    }

    public void addLoadProgress(int pr){
        intProgresoCarga=intProgresoCarga+pr;
        progressBar.setProgress(intProgresoCarga);
    }

    public void cargarUsuario(){
        System.out.println("cargara usuario");
        System.out.println(userId);
        if(userId!=null) {
            GameDataFac.cargaDataUsuario(database,userId, new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    //Post post = dataSnapshot.getValue(Post.class);
                    System.out.println("Se cargara la config---");
                    if (dataSnapshot.exists()) {      //Ya existe la data del usuario
                        userGData = dataSnapshot.getValue(UserGameData.class);
                        completedActivs = userGData.getActividadesCompletadas();
                    } else {
                        userGData = GameDataFac.emptyGame();
                        userGData.setHerramientas(config.getTools());
                        GameDataFac.setUserGameData(database,userId, userGData);
                    }
                    addLoadProgress(25);
                    refrescaHome();
                    System.out.println("---Se cargo la config");
                    cargoUserData = true;
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
    }

    public void chooseLevel() {
        for (Map.Entry<String, Nivel> entry : config.getNiveles().entrySet()) {
            Nivel n = (Nivel)entry.getValue();
            if(userGData.punteo>n.getPuntos_menor() && userGData.punteo<n.getPuntos_mayor()){
                if(userGData.getNivel()!=n.getId()){
                    //cambió de nivel
                }else{
                    //sigue en mismo nivel
                }
            }
        }
    }

    public void refrescaHome(){
        chooseLevel();
        homeViewModel.setmText(userGData);
        homeViewModel.setUserId(userId);
        System.out.println("Actualiza punteo:");
        System.out.println(userGData.punteo.toString());
    }

    public void enviarAListado(View view){
        mpBack.stop();
        mpButton.start();
        Intent intent = new Intent(view.getContext(), ListaDinamicas.class);
        intent.putExtra(APP_CONFIG,config.getCategory("cyberseguridad"));
        intent.putExtra(APP_USERID,userId);
        intent.putExtra(APP_COMPLETED,completedActivs);
        startActivity(intent);
    }

    public void enviarAListado2(View view){
        mpBack.stop();
        Intent intent = new Intent(view.getContext(), ListaDinamicas.class);
        intent.putExtra(APP_CONFIG,config.getCategory("cyberdelitos"));
        intent.putExtra(APP_USERID,userId);
        intent.putExtra(APP_COMPLETED,completedActivs);
        startActivity(intent);
    }



    public void enviarAListado3(View view){
        mpBack.stop();
        Intent intent = new Intent(view.getContext(), ListaDinamicas.class);
        intent.putExtra(APP_CONFIG,config.getCategory("enaccion"));
        intent.putExtra(APP_USERID,userId);
        intent.putExtra(APP_COMPLETED,completedActivs);
        startActivity(intent);
    }

    public void loadJSONConfig(){
        System.out.println("cargara el JSON");
        // [START post_value_event_listener]
        ConfigFactory.loadConfiguration(database,new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                config = ConfigFactory.buildConfiguration((Map<String,Object>) dataSnapshot.getValue());
                cargoConfig=true;
                addLoadProgress(25);
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
            LinearLayout cargador = findViewById(R.id.layoutLoader);
            cargador.setVisibility(View.GONE);
            if(localPrefs.getBoolean("firstrun",true)){
                muestraVideo=true;

                capaVideo.setVisibility(View.VISIBLE);
                objVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        muestraVideo=false;
                        capaVideo.setVisibility(View.INVISIBLE);
                        mpBack.start();
                    }
                });
                objVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.video_inicio));
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(objVideo);
                objVideo.setMediaController(mediaController);
                mediaController.setMediaPlayer(objVideo);
                objVideo.requestFocus();
                objVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaController.setAnchorView(objVideo);
                    }
                });
                objVideo.start();
                localPrefs.edit().putBoolean("firstrun",false).commit();
            }else {
                capaVideo.setVisibility(View.INVISIBLE);
                mpBack.start();
            }
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
            readOnlineFile(myurl.getUrl(), myurl.getID());
            if(al.size()>0)
                downloadAndSave(al);
        }catch(Exception e){
            System.out.println("Error al abrir el archivo");
        }
    }

    private void readOnlineFile(String strUrl,String actId){
        new Thread(new Runnable(){

            public void run(){
                String finalStr="";


                try {
                    // Create a URL for the desired page
                    System.out.println("url:");
                    System.out.println(strUrl);
                    if(!strUrl.isEmpty()) {
                        URL url = new URL(strUrl); //My text file location
                        //First open the connection
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(8000); // timing out in a minute

                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        //t=(TextView)findViewById(R.id.TextView1); // ideally do this in onCreate()
                        String str;
                        while ((str = in.readLine()) != null) {
                            finalStr += str + '\n';
                        }
                        if(!finalStr.isEmpty()) {
                            FileOutputStream fos = getBaseContext().openFileOutput(actId + "_content.html", Context.MODE_PRIVATE);
                            fos.write(finalStr.getBytes());
                            fos.close();
                        }

                        in.close();
                    }

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

    public void restartSound(){
        if(mpBack!=null) {
            mpBack.setOnPreparedListener(this);
            //mpBack.prepareAsync();
        }
    }

    public void onPrepared(MediaPlayer m){
        m.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //updateUI(currentUser);
        //restartSound();
        userId = userFac.getCurrentUser();
    }

    @Override
    public void onPause(){
        super.onPause();
        mpBack.stop();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mpBack.stop();
    }

    public void onRestart(){
        super.onRestart();
        //restartSound();
    }

    @Override
    public void onResume(){
        super.onResume();
        //restartSound();
        cargarUsuario();

//        mpBack.start();
    }

    public FirebaseDatabase getDB(){
        return database;
    }

}