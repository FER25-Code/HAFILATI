package com.map.fer.t_bus.Bus;
import android.widget.Filter;
import java.util.ArrayList;

public class CustomFilterBus extends Filter {

    BusAdapter adapterbus;
    ArrayList<MyBus> filterList;
    public CustomFilterBus(ArrayList<MyBus> filterList, BusAdapter busAdapter)
    {
        this.adapterbus =busAdapter;
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
        adapterbus.myBuses = (ArrayList<MyBus>) results.values;
        //REFRESH
        adapterbus.notifyDataSetChanged();
    }
}

