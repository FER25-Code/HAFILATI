package com.map.fer.t_bus.Line;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import com.map.fer.t_bus.R;

import java.util.ArrayList;

public class LineAdapter  extends RecyclerView.Adapter<LineAdapter.ViewHolder> implements Filterable { //creat lineAdapter for setting data
    ArrayList<MyLine> myLineArrayList, filterListline;
    CustomFilterLine filterLine;
    Context context;
    public LineAdapter(Context context, ArrayList<MyLine> myLineArrayList) {//constrector
        this.context=context;
        this.myLineArrayList = myLineArrayList;
        this.filterListline =myLineArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.line_item, viewGroup, false);
        return new LineAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {//set view and text to card view
        final MyLine line = myLineArrayList.get(i);
        viewHolder.Line.setText(line.getName());
        viewHolder.Nbr.setText(String.valueOf(line.getNbrLine()));

        viewHolder.setItemClickListener(new ItemClickListenerLine() {
            @Override
            public void onItemClick(View v, int pos) {

                Intent i = new Intent(context, LineStation.class);  //when click on item pass to next ativity
                Bundle bundle = new Bundle();
                bundle.putInt("line_number", line.getNbrLine());
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
    //    return myLineArrayList.size();
        return (myLineArrayList == null) ? 0 : myLineArrayList.size();
    }


    @Override
    public android.widget.Filter getFilter() {
        if(filterLine ==null)
        {
            filterLine =new CustomFilterLine(filterListline,this);
        }
        return filterLine;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { // define components of item
        private TextView Line , Nbr;
        ItemClickListenerLine itemClickListener;
        public ViewHolder(View view) {
            super(view);

            Line =  itemView.findViewById(R.id.Lineitem);
            Nbr =  itemView.findViewById(R.id.Nbritem);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListenerLine ic)
        {
            this.itemClickListener=ic;
        }}


}
