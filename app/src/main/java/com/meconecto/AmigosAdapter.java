package com.meconecto;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meconecto.data.Actividad;

import java.util.HashMap;
import java.util.List;

public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.ViewHolder>  {

    private List<HashMap<String,String>> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomAmigo;
        TextView txtPunteo;
        ImageView imgAva;

        public ViewHolder(View view) {
            super(view);
            txtNomAmigo = view.findViewById(R.id.txtNombreAmigo);
            txtPunteo = view.findViewById(R.id.txtNivelAmigo);
            imgAva = view.findViewById(R.id.imgAvatarAmigo);
        }

        @SuppressLint("ResourceType")
        public void configurar(HashMap<String,String> txt){
            txtNomAmigo.setText(txt.get("nombre"));
            txtPunteo.setText(txt.get("punteo"));
            imgAva.setImageResource(Integer.parseInt(txt.get("img")));
        }

    }

    public AmigosAdapter(List<HashMap<String,String>> dataSet) {
        localDataSet = dataSet;
    }


    @NonNull
    @Override
    public AmigosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.amigo_row, parent, false);

        AmigosAdapter.ViewHolder vh = new AmigosAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        System.out.println("mostrara");
        System.out.println(position);
        holder.configurar(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        System.out.println("tiene:");
        System.out.println(localDataSet.size());
        return localDataSet.size();
    }
}
