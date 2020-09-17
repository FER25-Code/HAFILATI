package com.map.fer.t_bus.Station;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.map.fer.t_bus.R;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> { //creat adapter for setting data
    ArrayList<MyStation> android;

    Context context;
    public FilterAdapter(Context context,ArrayList<MyStation> android) {//constrector
        this.context=context;
        this.android = android;

    }

    @Override
    public FilterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bus_item, viewGroup, false);
        return new FilterAdapter.ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(FilterAdapter.ViewHolder viewHolder, int i) {//set view and text to card view
        final MyStation s = android.get(i);

//        viewHolder.station.setText( s .getstation());
        viewHolder.arrival_time.setText(s.getarrival_time());
        viewHolder.bus.setText(s.getBus());
        viewHolder.departure_time.setText(s.getdeparture_time());




    }







    @Override
    public int getItemCount() {
        return android.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { // define components of item
         TextView station , bus;
        TextView arrival_time;
        TextView departure_time;

        ItemClickListener itemClickListener;
        public ViewHolder(View view) {
            super(view);

            station = (TextView) itemView.findViewById(R.id.description);
            bus = (TextView) itemView.findViewById(R.id.nbus);
            arrival_time = (TextView) itemView.findViewById(R.id.time1);
            departure_time = (TextView) itemView.findViewById(R.id.time2);

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

