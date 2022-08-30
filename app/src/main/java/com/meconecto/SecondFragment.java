package com.meconecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
                // Handle the back button even
                new AlertDialog.Builder(mContext)
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                System.out.println("Saldrá el usuario");
                                ((ListaDinamicas)getActivity()).updateUserGameData(proxyWeb.getPunteo());
                                NavHostFragment.findNavController(SecondFragment.this).popBackStack();

                                //getActivity().getSupportFragmentManager().popBackStack();
                                /*NavHostFragment.findNavController(SecondFragment.this)
                                        .navigate(R.id.action_SecondFragment_to_FirstFragment);*/
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
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
        proxyWeb = new WebActivities(this.getContext(),selectedActivity);
        WebView myWebView = binding.wWeb;
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(proxyWeb,"Android");
        try {
            FileInputStream fis = this.getContext().openFileInput(selectedActivity.getId() + "_content.html");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    System.out.println("fila leida");
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
                System.out.println("contenido del archivo:");
                System.out.println(stringBuilder.toString());
                String baseUrl = "http://webymovil.com/";
                myWebView.loadDataWithBaseURL(baseUrl,stringBuilder.toString(),"text/html",null,baseUrl);
            } catch (IOException e) {
                System.out.println("Error al leer el archivo");
                // Error occurred when opening raw file for reading.
            } finally {
                String contents = stringBuilder.toString();
            }
            //String path = "file:///"+Environment.DIRECTORY_DOWNLOADS+"/"+selectedActivity.getId()+"/miarchivo.txt";
            //myWebView.loadUrl(path);
        }catch(FileNotFoundException e){
            System.out.println("no encuentra el archivo");
        }
    }

    @Override
    public void onDestroyView() {
        callback.remove();
        super.onDestroyView();
        binding = null;
    }



}