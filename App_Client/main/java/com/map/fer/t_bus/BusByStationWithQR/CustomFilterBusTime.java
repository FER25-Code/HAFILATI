package com.map.fer.t_bus.BusByStationWithQR;

import android.widget.Filter;

import com.map.fer.t_bus.Bus.MyBus;

import java.util.ArrayList;

public class CustomFilterBusTime extends Filter {

    TimeBusAdapter adapter;
    ArrayList<MyBus> filterList;
    public CustomFilterBusTime(ArrayList<MyBus> filterList, TimeBusAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;
    }
    //FILTERING OCURS
    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results=new Filter.FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<MyBus> filteredPlayers=new ArrayList<>();
            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getBus().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }
    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.android = (ArrayList<MyBus>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}

