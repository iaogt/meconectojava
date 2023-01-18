package com.meconecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.meconecto.data.Actividad;
import com.meconecto.data.Categoria;
import com.meconecto.databinding.FragmentSecondBinding;
import com.meconecto.ui.modals.Modal1;
import com.meconecto.ui.modals.Modal4;
import com.meconecto.web.WebActivities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class SecondFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;

    private FragmentSecondBinding binding;

    private FirstFragmentModel firstViewModel;

    private Actividad selectedActivity;

    WebActivities proxyWeb;

    OnBackPressedCallback callback;

    Context mContext;

    class DatosObserver2 implements Observer {
        @Override
        public void onChanged(Object o) {
            Actividad a = (Actividad) o;
            selectedActivity = a;
            loadUrl();
        }
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mContext = this.getContext();
        callback = new OnBackPressedCallback(true /* enabled by default*/ ) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button eve
                Modal4 confirmDialog = new Modal4();
                confirmDialog.setCerrarClickSi(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavHostFragment.findNavController(SecondFragment.this).popBackStack();
                        confirmDialog.dismiss();
                    }
                });
                confirmDialog.show(requireActivity().getSupportFragmentManager(), Modal4.TAG);
            }
        };

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        firstViewModel = new ViewModelProvider(requireActivity()).get(FirstFragmentModel.class);
        firstViewModel.getSelectedActivity().observe(getViewLifecycleOwner(),new DatosObserver2());

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, SecondFragment.this.getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, SecondFragment.this.getClass().getSimpleName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void cerrarExitoso(){
        ((ListaDinamicas)getActivity()).updateUserGameData(proxyWeb.getPunteo(),selectedActivity.getId());
        //((ListaDinamicas)getActivity()).checkLogros();
        NavHostFragment.findNavController(SecondFragment.this).popBackStack();
    }

    public void cerrarFallido(){
        //((ListaDinamicas)getActivity()).updateUserGameData(proxyWeb.getPunteo());
        NavHostFragment.findNavController(SecondFragment.this).popBackStack();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /*binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/
    }

    private void loadUrl(){
        Bundle bundle2 =new Bundle();
        bundle2.putString("game_name",selectedActivity.getTitulo());
        mFirebaseAnalytics.logEvent("open_game",bundle2);
        proxyWeb = new WebActivities(this.getChildFragmentManager(),selectedActivity,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarExitoso();
            }
        },new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarFallido();
            }
        });

        WebView myWebView = binding.wWeb;
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        myWebView.addJavascriptInterface(proxyWeb,"Android");
        try {
            FileInputStream fis = this.getContext().openFileInput(selectedActivity.getId() + "_content.html");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    Log.i("meconecto:","fila leida");
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
                Log.i("meconecto:","contenido del archivo:");
                Log.i("meconecto:",stringBuilder.toString());
                String baseUrl = "http://webymovil.com/";
                myWebView.loadDataWithBaseURL(baseUrl,stringBuilder.toString(),"text/html",null,baseUrl);
            } catch (IOException e) {
                Log.i("meconecto:","Error al leer el archivo");
                // Error occurred when opening raw file for reading.
            } finally {
                String contents = stringBuilder.toString();
            }
            //String path = "file:///"+Environment.DIRECTORY_DOWNLOADS+"/"+selectedActivity.getId()+"/miarchivo.txt";
            //myWebView.loadUrl(path);
        }catch(FileNotFoundException e){
            Log.i("meconecto:","no encuentra el archivo");
        }
    }

    @Override
    public void onDestroyView() {
        callback.remove();
        super.onDestroyView();
        binding = null;
    }



}