package com.map.fer.t_bus.TICKETS;

import android.widget.Filter;



import java.util.ArrayList;

public class CustomFilterTicket  extends Filter {

   TicketAdapter adapter;
    ArrayList<MyTicket> filterList;
    public CustomFilterTicket(ArrayList<MyTicket> filterList, TicketAdapter adapter)
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
            ArrayList<MyTicket> filteredPlayers=new ArrayList<>();
            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
//                if(filterList.get(i).getBus().toUpperCase().contains(constraint))
//                {
//                    //ADD PLAYER TO FILTERED PLAYERS
//                    filteredPlayers.add(filterList.get(i));
//                }
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
        adapter.android = (ArrayList<MyTicket>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}

