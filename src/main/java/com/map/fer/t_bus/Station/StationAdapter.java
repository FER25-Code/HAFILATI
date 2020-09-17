package com.map.fer.t_bus.Station;

import android.content.Context;
import android.content.Intent;

import com.map.fer.t_bus.R;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder>implements Filterable { //creat adapter for setting data
    ArrayList<MyStation> android,filterList;
    CustomFilter filter;
    Context context;
    public StationAdapter(Context context,ArrayList<MyStation> android) {//constrector
        this.context=context;
        this.android = android;
        filterList=android;

    }

    @Override
    public StationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.station_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StationAdapter.ViewHolder viewHolder, int i) {//set view and text to card view
        final MyStation s = android.get(i);

        viewHolder.station.setText( s .getstation());


        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent i = new Intent(context, StationView.class);  //when click on item pass to next ativity
                Bundle bundle = new Bundle();    //get and past informations with bundle from main activity to next activity
                bundle.putString("arrival_time", s.getarrival_time());
                bundle.putString("busnbr", s.getBus());
                bundle.putString("station", s.getstation());
                bundle.putDouble("latitude", s.getlaltitude());
                bundle.putDouble("longitude", s.getlongitude());
                bundle.putString("departure_time", s.getdeparture_time());

                //bundle.putSerializable("obj", (Serializable) data);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

            }

    @Override
    public int getItemCount() {
        return android.size();
    }


    @Override
    public android.widget.Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }
        return filter;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { // define components of item
        private TextView station ;
        ItemClickListener itemClickListener;
        public ViewHolder(View view) {
            super(view);
            station = (TextView) itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }}


}
