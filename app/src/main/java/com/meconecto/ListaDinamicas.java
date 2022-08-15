package com.meconecto;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.meconecto.data.AppConfiguration;
import com.meconecto.data.Categoria;
import com.meconecto.databinding.ActivityListaDinamicasBinding;
import com.meconecto.ui.home.HomeViewModel;

public class ListaDinamicas extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    private AppBarConfiguration appBarConfiguration;
    private ActivityListaDinamicasBinding binding;

    private FirstFragmentModel firstViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        binding = ActivityListaDinamicasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Categoria appC = (Categoria) getIntent().getSerializableExtra(MainActivity.APP_CONFIG);
        firstViewModel = new ViewModelProvider(this).get(FirstFragmentModel.class);
        firstViewModel.setCategory(appC);

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
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_lista_dinamicas);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}