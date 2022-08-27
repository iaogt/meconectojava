package com.meconecto.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.meconecto.R;
import com.meconecto.ui.amigos.AmigosListAdapter;

import java.util.ArrayList;

public class LogrosAdapter extends RecyclerView.Adapter<LogrosAdapter.ViewHolder> {

    private ArrayList<String> localDataSet;
    private final LogrosAdapter.OnItemClickListener mylistener;


    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textView3;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.textView);
            textView3 = (TextView) view.findViewById((R.id.textView3));
        }

        public TextView getTextView() {
            return textView;
        }

        public void configurar(String txt, final LogrosAdapter.OnItemClickListener mlistener){

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    mlistener.onItemClick(txt);
                }
            });
        }
    }

    public LogrosAdapter(ArrayList<String> dataSet, LogrosAdapter.OnItemClickListener listener){
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
