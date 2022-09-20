package com.meconecto.ui.modals;

import android.app.AlertDialog;
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

public class Modal3 extends DialogFragment {

    public static String TAG = "Modal3";
    public View v;
    String nombreLogro;
    public View.OnClickListener cerrarClick;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.modal_logro1,container);
        TextView tx = v.findViewById(R.id.nomLogro);
        tx.setText(nombreLogro);
        Button b = v.findViewById(R.id.button);
        b.setOnClickListener(cerrarClick);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void setCerrarClick(View.OnClickListener c){
        cerrarClick = c;
    }

    public void setNombreLogro(String nom){ nombreLogro = nom;}

}
