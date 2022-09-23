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

public class Modal4 extends DialogFragment {

    public static String TAG = "Modal4";
    public View v;
    String nombreLogro;
    public View.OnClickListener cerrarClickSi;
    public View.OnClickListener cerrarClickNo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.modal_confirm,container);
        Button b = v.findViewById(R.id.button2);
        Button c = v.findViewById(R.id.button3);
        b.setOnClickListener(cerrarClickSi);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void setCerrarClickSi(View.OnClickListener c){
        cerrarClickSi = c;
    }



    public void setNombreLogro(String nom){ nombreLogro = nom;}

}
