package com.meconecto.ui.modals;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.meconecto.R;
import com.meconecto.SecondFragment;

public class Modal2 extends DialogFragment {

    public static String TAG = "Modal2";
    public View v;
    Long puntos;
    public View.OnClickListener cerrarClick;
    private MediaPlayer mpButton;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.modal_fail,container);
        Button b = v.findViewById(R.id.btnSample);
        b.setOnClickListener(cerrarClick);
        mpButton = MediaPlayer.create(getContext(), R.raw.sounderror);
        mpButton.start();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void setCerrarClick(View.OnClickListener c){
        cerrarClick = c;
    }

    public void setPuntos(Long p){

        puntos = p;
    }
}
