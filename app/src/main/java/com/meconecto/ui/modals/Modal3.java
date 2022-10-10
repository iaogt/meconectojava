package com.meconecto.ui.modals;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.meconecto.R;
import com.meconecto.SecondFragment;

import java.util.HashMap;

public class Modal3 extends DialogFragment {

    public static String TAG = "Modal3";
    public View v;
    String nombreLogro;
    public View.OnClickListener cerrarClick;
    public HashMap<String,String> dataLogro;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.modal_logro1,container);
        TextView tx = v.findViewById(R.id.nomLogro);
        if(dataLogro!=null)
        {
            tx.setText(dataLogro.get("nombre"));
            ImageView img = v.findViewById(R.id.imgLogro);
            img.setImageResource(Integer.parseInt(dataLogro.get("imagen")));
        }
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

    public void setDataLogro(HashMap<String,String> d){ dataLogro = d; }

}
