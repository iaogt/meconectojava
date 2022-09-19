package com.meconecto;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.meconecto.data.Actividad;

import org.w3c.dom.Text;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(Actividad item);
    }

    private List<Actividad> localDataSet;
    private final OnItemClickListener mylistener;
    private String completedActivs;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textView3;
        private final TextView puntos;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.textView);
            textView3 = (TextView) view.findViewById((R.id.textView3));
            puntos = (TextView) view.findViewById((R.id.txtPuntos));
        }

        public TextView getTextView() {
            return textView;
        }

        public void configurar(Actividad txt, final OnItemClickListener mlistener,String completed){
            if(txt.getCompletada()){   //Ya completó esta actividad
                System.out.println("Si existe");
                itemView.setBackgroundColor(Color.parseColor("#00ff00"));
            }else{
                System.out.println("No existe en la lista de completadas");
            }
            textView.setText(txt.getTitulo());
            textView3.setText(txt.getDesc());
            puntos.setText(txt.getExito().toString());
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    mlistener.onItemClick(txt);
                }
            });
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(List<Actividad> dataSet,OnItemClickListener listener) {
        localDataSet = dataSet;
        mylistener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.configurar(localDataSet.get(position),mylistener,completedActivs);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        System.out.print("Se llamo este proc");
        return localDataSet.size();
    }
}