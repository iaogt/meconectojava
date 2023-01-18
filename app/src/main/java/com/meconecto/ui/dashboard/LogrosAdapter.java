package com.meconecto.ui.dashboard;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.meconecto.R;
import com.meconecto.data.IconLogro;
import com.meconecto.ui.amigos.AmigosListAdapter;

import java.util.ArrayList;

public class LogrosAdapter extends RecyclerView.Adapter<LogrosAdapter.ViewHolder> {

    private ArrayList<IconLogro> localDataSet;
    private final LogrosAdapter.OnItemClickListener mylistener;


    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbLogro;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            thumbLogro = (ImageView) view.findViewById(R.id.thumbLogro);
        }

        public ImageView getThumbView() {
            return thumbLogro;
        }

        public void configurar(IconLogro idImg, final LogrosAdapter.OnItemClickListener mlistener){
            thumbLogro.setImageResource(Integer.parseInt(idImg.imagen));
            if(idImg.estado=="i") {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);  //0 means grayscale
                ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
                thumbLogro.setColorFilter(cf);
            }
            /*itemView.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    mlistener.onItemClick(txt);
                }
            });*/
        }
    }

    public LogrosAdapter(ArrayList<IconLogro> dataSet, LogrosAdapter.OnItemClickListener listener){
        localDataSet=dataSet;
        mylistener = listener;
    }

    @Override
    public LogrosAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_logro, viewGroup, false);

        LogrosAdapter.ViewHolder vh = new LogrosAdapter.ViewHolder(view);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(LogrosAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.configurar(localDataSet.get(position),mylistener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
