package com.map.fer.t_bus.Line;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilterLine  extends Filter {

    LineAdapter lineAdapter;
    ArrayList<MyLine> filterListline;
    public CustomFilterLine(ArrayList<MyLine> filterList, LineAdapter lineAdapter1)
    {
        this.lineAdapter =lineAdapter1;
        this.filterListline =filterList;
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
            ArrayList<MyLine> filteredPlayers=new ArrayList<>();
            for (int i = 0; i< filterListline.size(); i++)
            {
                //CHECK
                if(filterListline.get(i).getLine().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterListline.get(i));
                }
            }
            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count= filterListline.size();
            results.values= filterListline;
        }
        return results;
    }
    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        lineAdapter.myLineArrayList = (ArrayList<MyLine>) results.values;
        //REFRESH
        lineAdapter.notifyDataSetChanged();
    }
}

