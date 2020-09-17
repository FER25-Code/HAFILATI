package com.map.rsd.busapplication.Alert;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.map.rsd.busapplication.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.ViewHolder> {

    ArrayList<MyAlert> android;

    Context context;
    public AlertsAdapter(Context context,ArrayList<MyAlert> android) {//constrector
        this.context=context;
        this.android = android;

    }

    @Override
    public AlertsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alert_item, viewGroup, false);
        return new AlertsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AlertsAdapter.ViewHolder viewHolder, int i) {//set view and text to card view
        final MyAlert s = android.get(i);

        viewHolder.name.setText( s .getName());
        viewHolder.desc.setText( s .getDescription());
        viewHolder.level.setText(String.valueOf(s .getLevel()));




    }

    @Override
    public int getItemCount() {
        return android.size();    }


    public class ViewHolder extends RecyclerView.ViewHolder { // define components of item
        private TextView level,desc,name ;
        public ViewHolder(View view) {
            super(view);

            level = (TextView) itemView.findViewById(R.id.linealert);
            desc = (TextView) itemView.findViewById(R.id.nbrLineAlert);
            name = (TextView) itemView.findViewById(R.id.alerttype);


        }














    }}
