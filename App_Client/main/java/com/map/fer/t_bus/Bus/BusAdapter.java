package com.map.fer.t_bus.Bus;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.map.fer.t_bus.R;
import java.util.ArrayList;

public class BusAdapter  extends RecyclerView.Adapter<BusAdapter.ViewHolder> implements Filterable { //creat adapterbus for setting data
    ArrayList<MyBus> myBuses;
    ArrayList<MyBus> filterListbus;
    CustomFilterBus filterbus;
    Context context;
    public BusAdapter(Context context, ArrayList<MyBus> myBuses) {//constrector
        this.context=context;
        this.myBuses = myBuses;
       this.filterListbus = myBuses;

    }

    @Override
    public BusAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bus2_item, viewGroup, false);
        return new BusAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BusAdapter.ViewHolder viewHolder, int i) {//set view and text to card view
        final MyBus bus = myBuses.get(i);
        String str =  bus.getStartTime();
        String[] arrOfStr = str.split(" ");
        String part1 = arrOfStr[0];
        final String part = arrOfStr[1];
        String str2 = bus.getArrivalTime();
        String[] arrOfStr1 = str.split(" ");
        String part11 = arrOfStr[0];
        final String part2 = arrOfStr[1];

     viewHolder.Line.setText( bus.getLinename());
        viewHolder.Nbr.setText( String.valueOf(bus.getNbrBus()));
        viewHolder.time.setText(part2);
        viewHolder.setItemClickListener(new ItemClickListenerBus() {
            @Override
            public void onItemClick(View v, int pos) {
            //    Snackbar.make(v,bus.getBus(),Snackbar.LENGTH_SHORT).show();
                Intent i = new Intent(context, BusView.class);  //when click on item pass to next ativity
                Bundle bundle = new Bundle();
                bundle.putInt("nbr_bus", bus.getNbrBus());
                bundle.putInt("idline",bus.getIdline());
                bundle.putString("name", bus.getLinename());
                bundle.putString("departure_date",part);
                bundle.putString("finish_date",part2);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
     //   return myBuses.size();
        return (myBuses == null) ? 0 : myBuses.size();
    }


    @Override
    public Filter getFilter() {
        if(filterbus ==null)
        {
            filterbus =new CustomFilterBus(filterListbus,this);
        }
        return filterbus;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { // define components of item
        private TextView Line , Nbr ,time;
        ItemClickListenerBus itemClickListener;
        public ViewHolder(View view) {
            super(view);

            Line =  itemView.findViewById(R.id.textLine);
            Nbr =  itemView.findViewById(R.id.nbrbus);
            time=itemView.findViewById(R.id.textTime);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListenerBus ic)
        {
            this.itemClickListener=ic;
        }}


}
