package com.map.rsd.busapplication.Event;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.map.rsd.busapplication.R;
import com.map.rsd.busapplication.SqLite.DB_sqlite;


import java.util.ArrayList;

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.ViewHolder> { //creat adapter for setting data
    ArrayList<Event_Item> android;
    Context context;

    public EventAdapter(Context context, ArrayList<Event_Item> android) {//constrector
        this.context = context;
        this.android = android;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {//set view and text to card view
        final Event_Item s = android.get(i);
        viewHolder.description.setText(s.getdescription());
        viewHolder.name.setText(s.getname());
        viewHolder.start_date.setText(s.getstart_date());
        viewHolder.end_date.setText(s.getend_date());


    }


    @Override
    public int getItemCount() {
        return android.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder { // define components of item
        private TextView description, name;
        private TextView start_date, end_date;

        public ViewHolder(View view) {
            super(view);

            description = (TextView) itemView.findViewById(R.id.description);
            name = (TextView) itemView.findViewById(R.id.name);
            start_date = (TextView) itemView.findViewById(R.id.start_date);
            end_date = (TextView) itemView.findViewById(R.id.end_date);

        }


    }

}