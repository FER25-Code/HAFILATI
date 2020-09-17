package com.map.fer.t_bus.TICKETS;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.map.fer.t_bus.R;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> implements Filterable {
    ArrayList<MyTicket> android;
    ArrayList<MyTicket> filterListbus;
    Context context;

    public TicketAdapter(Context context, ArrayList<MyTicket> android) {//constrector
        this.context=context;
        this.android = android;
        filterListbus = android;

    }
    @NonNull
    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tickets_item, viewGroup, false);
        return new TicketAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.ViewHolder viewHolder, int i) {
        final MyTicket myTicket = android.get(i);
        viewHolder.Line.setText("Line Nbr: "+myTicket.getNbrLine());
        viewHolder.Nbr.setText( "Line :"+myTicket.getNameLine());
        viewHolder.date.setText(myTicket.getDate());
        viewHolder.time.setText(myTicket.getTime());
        viewHolder.Amount.setText(String.valueOf(myTicket.getAmount()));

    }

    @Override
    public int getItemCount() {
        return android.size();

    }

    @Override
    public Filter getFilter() {
        return null;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { // define components of item
        private TextView Line , Nbr ,time,date,Amount;
        ItemClickListenerTicket itemClickListener;
        public ViewHolder(View view) {
            super(view);
            Line =  itemView.findViewById(R.id.lineticket);
            Nbr =  itemView.findViewById(R.id.nbrbTicket);
            date =  itemView.findViewById(R.id.date);
            Amount =  itemView.findViewById(R.id.amount);
            time=itemView.findViewById(R.id.timeticket);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListenerTicket ic)
        {
            this.itemClickListener=ic;
        }}
}